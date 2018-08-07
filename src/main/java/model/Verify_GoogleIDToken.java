package main.java.model;///*package main.java.model;
//
//
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.Scanner;
//
//import static com.sun.xml.internal.ws.api.message.Packet.Status.Request;
//import static com.sun.xml.internal.ws.api.message.Packet.Status.Response;
//
//
//public class Verify_GoogleIDToken {
//
//        /**
//         * Replace this with the client ID you got from the Google APIs console.
//         */
//        private static final String CLIENT_ID = "YOUR_CLIENT_ID";
//        private static final String APPLICATION_NAME = "Shopal";
//
//        /**
//         * Default HTTP transport to use to make HTTP requests.
//         */
//        private static final HttpTransport TRANSPORT = new NetHttpTransport();
//        /**
//         * Default JSON factory to use to deserialize JSON.
//         */
//        private static final JacksonFactory JSON_FACTORY = new JacksonFactory();
//        /**
//         * Gson object to serialize JSON responses to requests to this servlet.
//         */
//        private static final Gson GSON = new Gson();
//
//        /**
//         * Register all endpoints that we'll handle in our server.
//         * @param args Command-line arguments.
//
//        public static void main(String[] args) {
//            // Initialize a session for the current user, and render index.html.
//            get(new Route("/") {
//                @Override
//                public Object handle(Request request, Response response) {
//                    response.type("text/html");
//                    try {
//                        // Fancy way to read index.html into memory, and set the client ID
//                        // in the HTML before serving it.
//                        return new Scanner(new File("index.html"), "UTF-8")
//                                .useDelimiter("\\A").next()
//                                .replaceAll("[{]{2}\\s*CLIENT_ID\\s*[}]{2}", CLIENT_ID)
//                                .replaceAll("[{]{2}\\s*APPLICATION_NAME\\s*[}]{2}",
//                                        APPLICATION_NAME);
//                    } catch (FileNotFoundException e) {
//                        // When running the sample, there was some path issue in finding
//                        // index.html.  Double check the guide.
//                        e.printStackTrace();
//                        return e.toString();
//                    }
//                }
//            });
//         */
//            // "Verify an ID Token or an Access Token.
//            // Tokens should be passed in the URL parameters of a POST request.
//            post(new Route("/verify") {
//                @Override
//                public Object handle(Request request, Response response) {
//                    response.type("application/json");
//
//                    String idToken = request.queryParams("id_token");
//                   //// String accessToken = request.queryParams("access_token");
//
//                    TokenStatus idStatus = new TokenStatus();
//                    if (idToken != null) {
//                        // Check that the ID Token is valid.
//
//                        Checker checker = new Checker(new String[]{CLIENT_ID}, CLIENT_ID);
//                        GoogleIdToken.Payload jwt = checker.check(idToken);
//
//                        if (jwt == null) {
//                            // This is not a valid token.
//                            idStatus.setValid(false);
//                            idStatus.setId("");
//                            idStatus.setMessage("Invalid ID Token.");
//                        } else {
//                            idStatus.setValid(true);
//                            String gplusId = (String)jwt.get("sub");
//                            idStatus.setId(gplusId);
//                            idStatus.setMessage("ID Token is valid.");
//                        }
//                    } else {
//                        idStatus.setMessage("ID Token not provided");
//                    }
//
//                    TokenStatus accessStatus = new TokenStatus();
//                    if (accessToken != null) {
//                        // Check that the Access Token is valid.
//                        try {
//                            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
//                            Oauth2 oauth2 = new Oauth2.Builder(
//                                    TRANSPORT, JSON_FACTORY, credential).build();
//                            Tokeninfo tokenInfo = oauth2.tokeninfo()
//                                    .setAccessToken(accessToken).execute();
//                            if (!tokenInfo.getIssuedTo().equals(CLIENT_ID)) {
//                                // This is not meant for this app. It is VERY important to check
//                                // the client ID in order to prevent man-in-the-middle attacks.
//                                accessStatus.setValid(false);
//                                accessStatus.setId("");
//                                accessStatus.setMessage("Access Token not meant for this app.");
//                            } else {
//                                accessStatus.setValid(true);
//                                accessStatus.setId(tokenInfo.getUserId());
//                                accessStatus.setMessage("Access Token is valid.");
//                            }
//                        } catch (IOException e) {
//                            accessStatus.setValid(false);
//                            accessStatus.setId("");
//                            accessStatus.setMessage("Invalid Access Token.");
//                        }
//                    } else {
//                        accessStatus.setMessage("Access Token not provided");
//                    }
//
//                    VerificationResponse tokenStatus =
//                            new VerificationResponse(idStatus, accessStatus);
//                    return GSON.toJson(tokenStatus);
//                }
//            });
//        }
//
//        /**
//         * JSON representation of a token's status.
//         */
//        public static class TokenStatus {
//            public boolean valid;
//            public String gplus_id;
//            public String message;
//
//            public TokenStatus() {
//                valid = false;
//                gplus_id = "";
//                message = "";
//            }
//
//            public void setValid(boolean v) {
//                this.valid = v;
//            }
//
//            public void setId(String gplus_id) {
//                this.gplus_id = gplus_id;
//            }
//
//            public void setMessage(String message) {
//                this.message = message;
//            }
//        }
//
//        /**
//         * JSON response to verification request.
//         *
//         * Example JSON response:
//         * {
//         *   "id_token_status": {
//         *     "info": "12345",
//         *     "valid": True
//         *   },
//         *   "access_token_status": {
//         *     "Access Token not meant for this app.",
//         *     "valid": False
//         *   }
//         * }
//         */
//        public static class VerificationResponse {
//            public TokenStatus id_token_status;
//            public TokenStatus access_token_status;
//
//            private VerificationResponse(TokenStatus _id_token_status, TokenStatus _access_token_status) {
//                this.id_token_status = _id_token_status;
//                this.access_token_status = _access_token_status;
//            }
//
//            public static VerificationResponse newVerificationResponse(TokenStatus id_token_status,
//                                                                       TokenStatus access_token_status) {
//                return new VerificationResponse(id_token_status,
//                        access_token_status);
//            }
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//public class Checker {
//
//    private final List mClientIDs;
//    private final String mAudience;
//    private final GoogleIdTokenVerifier mVerifier;
//    private final JsonFactory mJFactory;
//    private String mProblem = "Verification failed. (Time-out?)";
//
//    public Checker(String[] clientIDs, String audience) {
//        mClientIDs = Arrays.asList(clientIDs);
//        mAudience = audience;
//        NetHttpTransport transport = new NetHttpTransport();
//        mJFactory = new GsonFactory();
//        mVerifier = new GoogleIdTokenVerifier(transport, mJFactory);
//    }
//
//    public GoogleIdToken.Payload check(String tokenString) {
//        GoogleIdToken.Payload payload = null;
//        try {
//            GoogleIdToken token = GoogleIdToken.parse(mJFactory, tokenString);
//            if (mVerifier.verify(token)) {
//                GoogleIdToken.Payload tempPayload = token.getPayload();
//                if (!tempPayload.getAudience().equals(mAudience))
//                    mProblem = "Audience mismatch";
//                else if (!mClientIDs.contains(tempPayload.getAuthorizedParty()))
//                    mProblem = "Client ID mismatch";
//                else
//                    payload = tempPayload;
//            }
//        } catch (GeneralSecurityException e) {
//            mProblem = "Security issue: " + e.getLocalizedMessage();
//        } catch (IOException e) {
//            mProblem = "Network problem: " + e.getLocalizedMessage();
//        }
//        return payload;
//    }
//
//    public String problem() {
//        return mProblem;
//    }
//}
//
//    val xhr = new XMLHttpRequest();
//xhr.open('POST', 'https://yourbackend.example.com/tokensignin');
//        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
//        xhr.onload = function() {
//        console.log('Signed in as: ' + xhr.responseText);
//        };
//        xhr.send('idtoken=' + id_token);
//
//
//
//
