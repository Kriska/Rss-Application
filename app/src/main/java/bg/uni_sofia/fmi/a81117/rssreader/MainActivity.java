package bg.uni_sofia.fmi.a81117.rssreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButtonApple;
    private RadioButton radioButtonAppleNews;
    private RadioButton radioButtonJava;
    private RadioButton radioButtonFuturism;
    private RadioButton radioButtonTechChurchG;
    private RadioButton radioButtonTechChurchM;
    private RadioButton radioButtonTechRadarG;
    private RadioButton radioButtonNew;
    private Button fetchButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
            radioButtonApple = (RadioButton) findViewById(R.id.radioBtnApple);
            radioButtonAppleNews = (RadioButton) findViewById(R.id.radioBtnAppleN);
            radioButtonJava = (RadioButton) findViewById(R.id.radioBtnJavaWrld);
            radioButtonFuturism = (RadioButton) findViewById(R.id.radioBtnFuturism);
            radioButtonTechChurchG = (RadioButton) findViewById(R.id.radioBtnTchrchG);
            radioButtonTechChurchM = (RadioButton) findViewById(R.id.radioBtnTchrchM);
            radioButtonTechRadarG = (RadioButton) findViewById(R.id.radioBtnTchrdG);
            radioButtonNew = (RadioButton) findViewById(R.id.radioBtnNew);
            fetchButton = (Button) findViewById(R.id.button);

            fetchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (radioButtonNew.isChecked()) {
                        Intent intent = new Intent(MainActivity.this, NewActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                        startActivity(intent);
                    } else if (radioButtonApple.isChecked()) {
                        loadRss("https://developer.apple.com/news/rss/news.rss");
                    } else if (radioButtonAppleNews.isChecked()) {
                    loadRss("https://www.apple.com/newsroom/rss-feed.rss");
                    } else if (radioButtonJava.isChecked()) {
                        loadRss("http://www.javaworld.com/index.rss");
                    } else if (radioButtonFuturism.isChecked()) {
                       loadRss("https://futurism.com/feed/");
                    } else if (radioButtonTechChurchG.isChecked()) {
                        loadRss("http://feeds.feedburner.com/TechCrunch/Google");
                    }
                    else if (radioButtonTechChurchM.isChecked()) {
                        loadRss("http://feeds.feedburner.com/TechCrunch/Microsoft");
                    }else if (radioButtonTechRadarG.isChecked()) {
                        loadRss("http://www.techradar.com/rss/reviews/gaming");
                    }
                }
            });


        }
        private void displayToast() {
            Toast.makeText(this, "Something went wrong. Please, try again later!",
                    Toast.LENGTH_LONG).show();
        }
        private void loadRss(String urlString) {
            try {
                Field field = RssActivity.class.getDeclaredField("urlString");
                field.setAccessible(true);
                field.set(null, urlString);
                Intent intent = new Intent(MainActivity.this, RssActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                startActivity(intent);
            } catch (Exception e) {
                displayToast();
            }
        }
}
