import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String returning = "";
    ArrayList<String> returned = new ArrayList<String>();
    String returns = "";
    int i = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("%s", returning);
        } 
        else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("t")) {
                for(int t = 0; t < returned.size(); t++) {
                    if(returned.get(t).contains(parameters[1])) {
                        returns = returns + returned.get(t) + "\n";
                    }
                }
            }
            return returns;
        }
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add-message")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    returning = returning + parameters[1] + '\n';
                    returned.add(parameters[1]);
                    return String.format("The String, %s ,has been added!", parameters[1]);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
