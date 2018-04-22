//package com.example.latte.compiler;
//
//import com.squareup.javapoet.JavaFile;
//import com.squareup.javapoet.TypeName;
//import com.squareup.javapoet.TypeSpec;
//
//import java.io.IOException;
//
//import javax.annotation.processing.Filer;
//import javax.lang.model.element.Modifier;
//import javax.lang.model.type.TypeMirror;
//import javax.lang.model.util.SimpleAnnotationValueVisitor7;
//
///**
// * Created by liangbingtian on 2018/3/29.
// */
//
//public final class AppRegisterVisitor extends SimpleAnnotationValueVisitor7 {
//
//    private Filer mFiler = null;
//    private TypeMirror mTypeMirror = null;
//    private String mPackageName = null;
//
//    public void setFiler(Filer filer) {
//        this.mFiler = filer;
//    }
//
//    @Override
//    public Object visitString(String s, Object o) {
//        mPackageName = s;
//        return o;
//    }
//
//    @Override
//    public Object visitType(TypeMirror t, Object o) {
//        mTypeMirror = t;
//        return o;
//    }
//
//    private void generateJavaCode() {
//        final TypeSpec targetActivity =
//                TypeSpec.classBuilder("AppRegister")
//                        .addModifiers(Modifier.PUBLIC)
//                        .addModifiers(Modifier.FINAL)
//                        .superclass(TypeName.get(mTypeMirror))
//                        .build();
//
//        final JavaFile javaFile = JavaFile.builder(mPackageName + ".wxapi", targetActivity)
//                .addFileComment("微信广播接收器")
//                .build();
//        try {
//            javaFile.writeTo(mFiler);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
