package com.yukhlin.rest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    private static final String SECURED_URL_PREFIX = "secured";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
            List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER);

            if (authHeader != null && authHeader.size() > 0) {
                String authToken = authHeader.get(0);
                authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
                String decodedString = new String(Base64.getDecoder().decode(authToken));
                String[] credentials = decodedString.split(":");
                String userName = credentials[0];
                String password = credentials[1];

                if (userName.equals("deeman") && password.equals("1")) {
                    return;
                }
            }

            Response unathorizedStatus = Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("User cannot acces it")
                    .build();
            requestContext.abortWith(unathorizedStatus);
        }
    }
}