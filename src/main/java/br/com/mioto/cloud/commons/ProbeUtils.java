package br.com.mioto.cloud.commons;

public class ProbeUtils {

    public static String normalizeMicroserviceName(String microservice) {

        if(microservice.contains("product")) {
            return "microservice-product-rest";

        } else if(microservice.contains("order")) {
            return "microservice-order-rest";

        } else if(microservice.contains("banner")) {
            return "microservice-banner-rest";

        } else if(microservice.contains("newsletter")) {
            return "microservice-newsletter-rest";

        } else if(microservice.contains("access_manager")) {
            return "microservice-access-manager-rest";

        } else if(microservice.contains("user_manager")) {
            return "microservice-user-manager-rest";

        } else if(microservice.contains("calendar")) {
            return "microservice-calendar-rest";

        } else if(microservice.contains("category")) {
            return "microservice-category-rest";

        } else if(microservice.contains("benefits")) {
            return "microservice-benefits-rest";

        } else if(microservice.contains("fare")) {
            return "microservice-fare-rest";
        }

        return microservice;
    }
}
