/*
 Copyright (c) 2012 GFT Appverse, S.L., Sociedad Unipersonal.

 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this
 file, You can obtain one at http://mozilla.org/MPL/2.0/.

 Redistribution and use in source and binary forms, with or without modification,
 are permitted provided that the conditions of the Mozilla Public License v2.0
 are met.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. EXCEPT IN CASE OF WILLFUL MISCONDUCT OR GROSS NEGLIGENCE, IN NO EVENT
 SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.
 */
package org.appverse.web.tools.codegenerator.util;

import org.apache.velocity.VelocityContext;

import java.io.File;

public class P2BConverterCreator extends AbstractModelCreator {
    static final String TEMPLATE_NAME = "velocity/converterP2B.vm";
    static final String PROPERTY_P2B_CONVERTER_PACKAGE = "P2B_CONVERTER_PACKAGE";
    static final String PROPERTY_P2B_CONVERTER_BEAN_NAME = "P2B_CONVERTER_BEAN_NAME";
    static final String PROPERTY_P2B_CONVERTER_SCOPE_NAME = "P2B_CONVERTER_SCOPE_NAME";
    static final String PROPERTY_P2B_CONVERTER_CLASS_NAME = "P2B_CONVERTER_CLASS_NAME";
    static final String PROPERTY_P2B_CONVERTER_BUSINESS_BEAN_NAME = "P2B_CONVERTER_BUSINESS_BEAN_NAME";
    //static final String PROPERTY_P2B_CONVERTER_INTEGRATION_BEAN_NAME = "P2B_CONVERTER_INTEGRATION_BEAN_NAME";
    static final String PROPERTY_P2B_CONVERTER_VO_BEAN_NAME = "P2B_CONVERTER_VO_BEAN_NAME";
    static final String PROPERTY_P2B_CONVERTER_IMPORTS = "P2B_CONVERTER_IMPORTS";

    public P2BConverterCreator(ServiceCreatorContext serviceCreatorContext, Class clPresentationModel) {
        super(serviceCreatorContext, clPresentationModel);
    }

    @Override
    VelocityContext getVelocityContext() {
        VelocityContext vc = new VelocityContext();
        vc.put(PROPERTY_P2B_CONVERTER_PACKAGE, serviceCreatorContext.getP2BConverterPackage());
        vc.put(PROPERTY_P2B_CONVERTER_BEAN_NAME, serviceCreatorContext.getBeanNameForConverter(getClModel())+ServiceCreatorContext.P2B_SUFFIX); //derive from clPresentationModel -> UserVO to user
        vc.put(PROPERTY_P2B_CONVERTER_SCOPE_NAME, serviceCreatorContext.getBeanNameForConverter(getClModel())); //derive from clPresentationModel -> UserVO to user
        vc.put(PROPERTY_P2B_CONVERTER_CLASS_NAME, serviceCreatorContext.getBusinessModelName(getClModel())+ServiceCreatorContext.P2B_SUFFIX);
        vc.put(PROPERTY_P2B_CONVERTER_BUSINESS_BEAN_NAME, serviceCreatorContext.getBusinessModelName(getClModel()));
        vc.put(PROPERTY_P2B_CONVERTER_VO_BEAN_NAME, getClModel().getSimpleName());
        //vc.put(PROPERTY_P2B_CONVERTER_INTEGRATION_BEAN_NAME, serviceCreatorContext.getIntegrationModelName(getClModel()));
        //Note additional imports should be the last, as all options may need additional imports.
        mImports.put(serviceCreatorContext.getBusinessModelPackage()+"."+serviceCreatorContext.getBusinessModelName(getClModel()),"");
        //mImports.put(serviceCreatorContext.getIntegrationModelPackage()+"."+serviceCreatorContext.getIntegrationModelName(getClModel()),"");
        mImports.put(getClModel().getName(),"");
        vc.put(PROPERTY_P2B_CONVERTER_IMPORTS, getAdditionalImports());
        return vc;
    }


    @Override
    String getTemplateName() {
        return TEMPLATE_NAME;
    }

    @Override
    public File getOutputFile() {
        String sDirs = serviceCreatorContext.getFileNameP2BConverter(serviceCreatorContext.getBusinessModelName(getClModel()) + ServiceCreatorContext.P2B_SUFFIX);
        sDirs = sDirs.substring(0, sDirs.lastIndexOf("/"));
        File fDirs = new File(sDirs);
        fDirs.mkdirs();

        File fNewClass = new File(serviceCreatorContext.getFileNameP2BConverter(serviceCreatorContext.getBusinessModelName(getClModel()) + ServiceCreatorContext.P2B_SUFFIX));
        return fNewClass;
    }
}
