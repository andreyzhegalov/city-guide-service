package cityguide.geocoder;

public class GetDataExample {

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("token arg not found");
            System.exit(1);
        }
        final String token = args[0];
        final var restController = new GeoCoder(token);
        final var result = restController.getSuggestions("г Санкт-Петербург, ул Садовая, д 2");
        System.out.println(result);
        System.exit(0);
    }
}
