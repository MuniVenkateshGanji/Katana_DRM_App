package com.jntucep.c19_delhi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class Youtube extends AppCompatActivity {

    RecyclerView recyclerView;
    Vector<YouTubeVideos> youtubeVideos = new Vector<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"wrap_content\" src=\"https://www.youtube.com/embed/XsaxavHUmN4?list=PLdrzoeK75zAU4Y6QAGxsNmXxgP6DTEIS_\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"wrap_content\" src=\"https://www.youtube.com/embed/jOFr1znBT0Q?list=PLzmrKicaleM2DR0fSuSMLZtP0gFtMX6ZT\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"wrap_content\" src=\"https://www.youtube.com/embed/awLnur5Yt9o\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"wrap_content\" src=\"https://www.youtube.com/embed/6LCMFHK1Xjw?list=PLoZP2WsNfBSEvDU9Egr26Tdr7IW7PmtD0\" frameborder=\"0\" allowfullscreen></iframe>") );
		youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"wrap_content\" src=\"https://www.youtube.com/embed/mzzvCGfKZR8?list=PLhnHFsOn0cgdjNrlX4qeytqAUIhRqKbAI\" frameborder=\"0\" allowfullscreen></iframe>") );

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);
    }
}