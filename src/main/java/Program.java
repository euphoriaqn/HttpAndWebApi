import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import youtube.entities.Activity;
import youtube.entities.ActivityResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Program extends Application {
    private static String URL = "http://www.youtube.com/embed/";
    private static String AUTO_PLAY = "?autoplay=1";
    private VBox root = new VBox(10);
    private ArrayList<VBox> vBoxess = new ArrayList<VBox>();
    private ArrayList<WebView> webviews = new ArrayList<WebView>();
    private static void initApplication() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public static void main(String[] args) throws UnirestException {
        initApplication();
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        final ScrollPane sp = new ScrollPane();


        sp.setContent(root);
        Scene scene = new Scene(sp);
        primaryStage.setScene(scene);
        final TextField channelId = new TextField();
 //       channelId.setTranslateX(10);
        channelId.setTranslateY(10);
        channelId.setMinSize(200, 10);

        final TextField maxResults = new TextField("5");
 //       maxResults.setTranslateX(100);
        maxResults.setMaxSize(30,10);
        maxResults.setTranslateY(10);

        final Button initButton = new Button("Init");
  //      initButton.setTranslateX(150);
        initButton.setTranslateY(10);
        final HBox hBox = new HBox();
        root.getChildren().addAll(hBox);
        initButton.setOnMouseClicked(event -> {
            Platform.runLater(()-> {
                try {
                    if (vBoxess != null && vBoxess.size() > 0) {
                        for (VBox value : vBoxess) {
                            value.getChildren().clear();
                        }
                        vBoxess.clear();
                        root.getChildren().clear();
                        root.getChildren().addAll(hBox);
                        if (webviews != null & webviews.size() > 0) {
                            for (WebView value : webviews) {
                                value.getEngine().load(null);
                            }
                            webviews.clear();
                        }
                    }
                    getActivity(channelId.getText(), maxResults.getText());

                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            });
        });

    //    hBox.setSpacing(15);
        hBox.getChildren().addAll(channelId, initButton, maxResults);

        primaryStage.setHeight(500);
        primaryStage.setWidth(800);
        primaryStage.show();
    }

    private void getActivity(String channelId, String maxResults) throws UnirestException {
            ActivityResponse response = null;
            Future<HttpResponse<ActivityResponse>> future = Unirest.get("https://www.googleapis.com/youtube/v3/search")
                    .queryString("q", channelId)
                    .queryString("type", "video")
                    .queryString("part", "snippet")
                    .queryString("maxResults", maxResults)
                    .queryString("key", "AIzaSyArYM5JuH6WpXt7n_AqBQM0uifsUjmf9H8")
                    .asObjectAsync(ActivityResponse.class);
            try {
                response = future.get().getBody();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        ActivityResponse activity = response;
        for(int i = 0; i < activity.getItems().size(); i++) {
            final Activity item = activity.getItems().get(i);
            final VBox vBox = new VBox();
            Button button = new Button("View " + i);
            Label nameOfVideo = new Label(item.getSnippet().getTitle());
            Label nameOfChannel = new Label(item.getSnippet().getChannelTitle());
            Label dateOfPublished = new Label(item.getSnippet().getPublishedAt().toString());
            ImageView imageView = ImageViewBuilder.create()
                    .image(new Image(item.getSnippet().getThumbnails().getMedium().getUrl()))
                    .build();
            vBox.getChildren().addAll(nameOfVideo,nameOfChannel,dateOfPublished,imageView,button);
            vBoxess.add(vBox);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    String videoId = item.getId().getVideoId();
                    System.out.println(videoId);

                    final WebView webview = new WebView();
                    webview.getEngine().load(
                            URL+videoId+AUTO_PLAY
                    );
                    webview.setPrefSize(640, 390);

                    final Button close = new Button("close");
                    close.setTranslateX(webview.getWidth() + 10);
                    close.setTranslateY(10);
                    close.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            webview.getEngine().load(null);
                            vBox.getChildren().removeAll(close,webview);
                        }
                    });

                    vBox.getChildren().addAll(webview,close);
                    webviews.add(webview);

                }
            });
        }
        root.getChildren().addAll(vBoxess);
    }
    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
