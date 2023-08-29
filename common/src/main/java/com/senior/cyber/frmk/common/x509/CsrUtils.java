package com.senior.cyber.frmk.common.x509;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class CsrUtils {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

}
