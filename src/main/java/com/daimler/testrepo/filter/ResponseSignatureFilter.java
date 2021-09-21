package com.daimler.testrepo.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Component
public class ResponseSignatureFilter implements Filter {
	final private String signKey ="signature";   //hardcoded but needs to be configurable 
	final private String signVal = "copyright signature"; //hardcoded but needs to be configurable
	@Override
	public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ByteArrayPrinter pw = new ByteArrayPrinter();
        HttpServletResponse wrappedResp = new HttpServletResponseWrapper(response) {

            @Override
            public void setContentType(final String type) {
                super.setContentType(MediaType.APPLICATION_JSON_VALUE);
            }

            @Override
            public PrintWriter getWriter() {
                return pw.getWriter();
            }

            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                return pw.getStream();
            }

        };
        chain.doFilter(httpRequest, wrappedResp);

        byte[] bytes = pw.toByteArray();
        String respBody = new String(bytes);
        
        Gson gson = new Gson();
		JsonObject responseJson = gson.fromJson(respBody, JsonObject.class);
		// Method 1 : We can add Copyright signature property 
		responseJson.addProperty(signKey, signVal);  //hardcoded as not now but can be configurable
		response.getWriter().print(responseJson);
		response.getWriter().flush();
		
		// Method 2 : We can create new Json Response object and 
		//            pass original message and copyright message to that 
		
	}

}
