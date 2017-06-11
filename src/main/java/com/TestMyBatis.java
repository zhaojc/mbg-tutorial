package com;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import java.io.IOException;
/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2017-02-28
 */
public class TestMyBatis {
    static ClassPool pool = ClassPool.getDefault();
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        modifyRefValid().writeFile("hello");
        modifyActivationResult().writeFile("hello");
        modifyJavaService().writeFile("hello");
        modifyRefProject().writeFile("hello");
    }
    private static CtClass modifyRefValid() throws NotFoundException, CannotCompileException {
        CtClass javaUtil = pool.get("com.seventh7.mybatis.util.JavaUtils");
        CtMethod refValid = javaUtil.getDeclaredMethod("refValid");
        refValid.setBody("{return true;}");
        return javaUtil;
    }
    private static CtClass modifyActivationResult() throws NotFoundException, CannotCompileException {
        CtClass driverClass = pool.get("com.seventh7.mybatis.ref.license.ActivationDriver");
        CtMethod activate = driverClass.getMethod("activate", "(Ljava/lang/String;)Lcom/seventh7/mybatis/ref/license/ActivationResult;");
        activate.setBody("{" +
//              "final com.intellij.openapi.util.Ref ref = com.intellij.openapi.util.Ref.create();" +
                "com.seventh7.mybatis.ref.license.LicenseData licenseData = new com.seventh7.mybatis.ref.license.LicenseData(\"1\", \"2\");" +
                "com.seventh7.mybatis.ref.license.ActivationResult res =com.seventh7.mybatis.ref.license.ActivationResult.success(licenseData);" +
//              "ref.set(res);" +
                " return res;}");
        return driverClass;
    }
    private static CtClass modifyRefProject() throws NotFoundException, CannotCompileException {
        CtClass refProject = pool.get("com.seventh7.mybatis.ref.RefProject");
        CtMethod notifyLicenseInvalid = refProject.getMethod("notifyLicenseInvalid", "()V");
        notifyLicenseInvalid.setBody("{return;}");
        return refProject;
    }
    private static CtClass modifyJavaService() throws NotFoundException, CannotCompileException {
        CtClass javaService = pool.get("com.seventh7.mybatis.service.JavaService");
        CtMethod stop = javaService.getMethod("stop", "()V");
        stop.setBody("{this.stopped = true;}");
        return javaService;
    }
}