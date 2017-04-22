package com.ckfinder.connector.configuration;

import javax.servlet.ServletConfig;

public class CkfinderConfiguration extends Configuration {

        String path = "";

        public CkfinderConfiguration(ServletConfig servletConfig) {
                super(servletConfig);
                path = servletConfig.getServletContext().getContextPath();
        }

        @Override
        public void init() throws Exception {
                super.init();
                this.baseURL = com.jeecms.cms.Constants.UPLOAD_PATH;
        }
}
