package com.senior.cyber.frmk.common.pki;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.ocsp.*;
import org.bouncycastle.cert.ocsp.jcajce.JcaCertificateID;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class CertificateUtils {

    public static X509Certificate read(String pem) throws IOException, CertificateException {
        try (PEMParser parser = new PEMParser(new StringReader(pem))) {
            Object object = parser.readObject();
            if (object instanceof JcaX509CertificateHolder) {
                JcaX509CertificateHolder holder = (JcaX509CertificateHolder) object;
                return new JcaX509CertificateConverter().getCertificate(holder);
            } else if (object instanceof X509CertificateHolder) {
                X509CertificateHolder holder = (X509CertificateHolder) object;
                return new JcaX509CertificateConverter().getCertificate(holder);
            } else {
                throw new java.lang.UnsupportedOperationException(object.getClass().getName());
            }
        }
    }

    public static X509Certificate read(File pem) throws IOException, CertificateException {
        return read(FileUtils.readFileToString(pem, StandardCharsets.UTF_8));
    }

    public static String write(X509Certificate certificate) throws IOException, CertificateEncodingException {
        StringWriter pem = new StringWriter();
        try (JcaPEMWriter writer = new JcaPEMWriter(pem)) {
            writer.writeObject(certificate);
        }
        return pem.toString();
    }

    public static List<String> lookupOcspUrl(X509Certificate certificate) throws IOException {
        byte[] bytes = certificate.getExtensionValue(Extension.authorityInfoAccess.getId());
        if (bytes == null) {
            return null;
        }
        ASN1Primitive asn1Primitive = null;
        try (ASN1InputStream asn1Stream = new ASN1InputStream(new ByteArrayInputStream(bytes))) {
            ASN1OctetString asn1Object = (ASN1OctetString) asn1Stream.readObject();
            try (ASN1InputStream stream = new ASN1InputStream(new ByteArrayInputStream(asn1Object.getOctets()))) {
                asn1Primitive = stream.readObject();
            }
        }
        if (asn1Primitive == null) {
            return null;
        }
        List<String> urls = new ArrayList<>();
        AuthorityInformationAccess authorityInformationAccess = AuthorityInformationAccess.getInstance(asn1Primitive);
        AccessDescription[] accessDescriptions = authorityInformationAccess.getAccessDescriptions();
        for (AccessDescription accessDescription : accessDescriptions) {
            boolean correctAccessMethod = accessDescription.getAccessMethod().equals(X509ObjectIdentifiers.ocspAccessMethod);
            if (!correctAccessMethod) {
                continue;
            }
            GeneralName name = accessDescription.getAccessLocation();
            if (name.getTagNo() != GeneralName.uniformResourceIdentifier) {
                continue;
            }
            ASN1TaggedObject taggedObject = (ASN1TaggedObject) name.toASN1Primitive();
            DERIA5String string = DERIA5String.getInstance(taggedObject, false);
            urls.add(string.getString());
        }
        return urls;
    }

    public static boolean ocspValidation(X509Certificate certificate, X509Certificate issuerCertificate, String ocspUri) throws OCSPException, OperatorCreationException, IOException, CertificateException {
        DigestCalculatorProvider digestCalculatorProvider = new JcaDigestCalculatorProviderBuilder().setProvider(BouncyCastleProvider.PROVIDER_NAME).build();
        CertificateID certificateID = new JcaCertificateID(digestCalculatorProvider.get(CertificateID.HASH_SHA1), issuerCertificate, certificate.getSerialNumber());
        OCSPReqBuilder ocspReqBuilder = new OCSPReqBuilder();
        ocspReqBuilder.addRequest(certificateID);
        OCSPReq ocspReq = ocspReqBuilder.build();

        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            ByteArrayEntity entity = new ByteArrayEntity(ocspReq.getEncoded(), ContentType.parse("application/ocsp-request"));
            HttpUriRequest request = RequestBuilder.post().setUri(ocspUri).setEntity(entity).build();
            try (CloseableHttpResponse response = client.execute(request)) {
                byte[] raw = EntityUtils.toByteArray(response.getEntity());
                OCSPResp ocspResponse = new OCSPResp(raw);

                BasicOCSPResp basicOCSPResp = (BasicOCSPResp) ocspResponse.getResponseObject();
                X509CertificateHolder[] certificateHolders = basicOCSPResp.getCerts();
                X509Certificate signerCert = new JcaX509CertificateConverter().getCertificate(certificateHolders[0]);
                JcaContentVerifierProviderBuilder jcaContentVerifierProviderBuilder = new JcaContentVerifierProviderBuilder();
                ContentVerifierProvider contentVerifierProvider = jcaContentVerifierProviderBuilder.build(signerCert.getPublicKey());
                if (basicOCSPResp.isSignatureValid(contentVerifierProvider)) {
                    SingleResp[] singleResps = basicOCSPResp.getResponses();
                    JcaX509CertificateHolder holder = new JcaX509CertificateHolder(issuerCertificate);
                    return singleResps[0].getCertID().matchesIssuer(holder, digestCalculatorProvider)
                            && singleResps[0].getCertID().getSerialNumber().compareTo((certificate).getSerialNumber()) == 0
                            && singleResps[0].getCertStatus() == null;
                }

            }
        }
        return false;
    }

    public static List<String> lookupCrlUrl(X509Certificate certificate) throws IOException {
        byte[] bytes = certificate.getExtensionValue(Extension.cRLDistributionPoints.getId());
        if (bytes == null) {
            return null;
        }
        ASN1Primitive asn1Primitive = null;
        try (ASN1InputStream asn1Stream = new ASN1InputStream(new ByteArrayInputStream(bytes))) {
            ASN1OctetString asn1Object = (ASN1OctetString) asn1Stream.readObject();
            try (ASN1InputStream stream = new ASN1InputStream(new ByteArrayInputStream(asn1Object.getOctets()))) {
                asn1Primitive = stream.readObject();
            }
        }
        if (asn1Primitive == null) {
            return null;
        }
        List<String> urls = new ArrayList<>();
        CRLDistPoint crlDistPoint = CRLDistPoint.getInstance(asn1Primitive);
        DistributionPoint[] distributionPoints = crlDistPoint.getDistributionPoints();
        for (DistributionPoint distributionPoint : distributionPoints) {
            GeneralNames generalNames = (GeneralNames) distributionPoint.getDistributionPoint().getName();
            for (GeneralName generalName : generalNames.getNames()) {
                DERIA5String string = (DERIA5String) generalName.getName();
                if (string.getString().startsWith("http://") || string.getString().startsWith("https://")) {
                    urls.add(string.getString());
                }
            }
        }
        return urls;
    }

}
