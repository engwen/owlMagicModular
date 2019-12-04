package com.owl.comment

import com.owl.comment.annotations.OwlLogInfo
import com.owl.util.LogPrintUtil
import groovy.transform.CompileStatic
import org.apache.tools.ant.taskdefs.Java

import java.util.jar.Attributes
import java.util.jar.Manifest

/**
 *
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/9/20.
 */
@CompileStatic
class OwlInit {
    static print() {
        URLClassLoader cl = (URLClassLoader) OwlLogInfo.class.getClassLoader();
        try {
            URL url = cl.findResource("META-INF/MANIFEST.MF");
            Manifest manifest = new Manifest(url.openStream());
            Attributes mainAttributes = manifest.getMainAttributes();
            String implVersion = mainAttributes.getValue("Implementation-Version");
            System.out.println(implVersion);
        } catch (IOException e) {
            // handle
        }
        LogPrintUtil.info("owlMagicComment start...")
    }
}
