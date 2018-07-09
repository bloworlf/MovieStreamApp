package com.example.mathurinbloworlf.moviestreamapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.mathurinbloworlf.moviestreamapp.R;
import com.example.mathurinbloworlf.moviestreamapp.adapter.ViewPagerAdapter;
import com.example.mathurinbloworlf.moviestreamapp.fragment.movie_feed.Latest;
import com.example.mathurinbloworlf.moviestreamapp.fragment.movie_feed.NowPlaying;
import com.example.mathurinbloworlf.moviestreamapp.fragment.movie_feed.Popular;
import com.example.mathurinbloworlf.moviestreamapp.fragment.movie_feed.TopRated;
import com.example.mathurinbloworlf.moviestreamapp.fragment.movie_feed.Upcoming;
import com.example.mathurinbloworlf.moviestreamapp.listener.AutoCompleteTextViewWatcher;

import java.util.List;

public class MovieFeedActivity extends AppCompatActivity {

    Toolbar toolbar;
    MenuItem search;

    private boolean isSearchOpened = false;
    private AutoCompleteTextView input_search;
    private TextInputLayout input_layout_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_feed);

        toolbar = findViewById(R.id.feed_toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.feed_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.feed_tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        search = menu.findItem(R.id.search);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_feed_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                showSearchInputText();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NowPlaying(), "Now Playing");
        adapter.addFragment(new TopRated(), "Top Rated");
        adapter.addFragment(new Popular(), "Popular");
        adapter.addFragment(new Latest(), "Latest");
        adapter.addFragment(new Upcoming(), "Upcoming");
        viewPager.setAdapter(adapter);
    }

    private Fragment getFocusedFragment(){
        Fragment fragment = null;
        //fragment = getSupportFragmentManager().findFragmentByTag("Now Playing");

        /*
        if(getSupportFragmentManager().findFragmentByTag("Now Playing").isVisible() &&
                getSupportFragmentManager().findFragmentByTag("Now Playing").isAdded() &&
                getSupportFragmentManager().findFragmentByTag("Now Playing").getUserVisibleHint()){
            fragment = getSupportFragmentManager().findFragmentByTag("Now Playing");
        }
        else if(getSupportFragmentManager().findFragmentByTag("Top Rated").isVisible() &&
                    getSupportFragmentManager().findFragmentByTag("Top Rated").isAdded() &&
                    getSupportFragmentManager().findFragmentByTag("Top Rated").getUserVisibleHint()){
                fragment = getSupportFragmentManager().findFragmentByTag("Top Rated");
        }
        else if(getSupportFragmentManager().findFragmentByTag("Popular").isVisible() &&
                getSupportFragmentManager().findFragmentByTag("Popular").isAdded() &&
                getSupportFragmentManager().findFragmentByTag("Popular").getUserVisibleHint()){
            fragment = getSupportFragmentManager().findFragmentByTag("Popular");
        }
        else if(getSupportFragmentManager().findFragmentByTag("Latest").isVisible() &&
                getSupportFragmentManager().findFragmentByTag("Latest").isAdded() &&
                getSupportFragmentManager().findFragmentByTag("Latest").getUserVisibleHint()){
            fragment = getSupportFragmentManager().findFragmentByTag("Latest");
        }
        else if(getSupportFragmentManager().findFragmentByTag("Upcoming").isVisible() &&
                getSupportFragmentManager().findFragmentByTag("Upcoming").isAdded() &&
                getSupportFragmentManager().findFragmentByTag("Upcoming").getUserVisibleHint()){
            fragment = getSupportFragmentManager().findFragmentByTag("Upcoming");
        }
        else {
            return null;
        }
        */

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment fragment1 : fragments){
            if(fragment1.isAdded() &&
                    fragment1.isVisible() &&
                    fragment1.getUserVisibleHint()){
                fragment = fragment1;
            }
        }

        return fragment;
    }

    public void showSearchInputText(){
        ActionBar actionbar = getSupportActionBar();    //get the actionbar

        if(isSearchOpened){
            //test if the search is open
            actionbar.setDisplayShowCustomEnabled(false);   //disable a custom view inside the actionbar
            actionbar.setDisplayShowTitleEnabled(true); //show the title in the actionbar

            //hide the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(input_search.getWindowToken(), 0);

            //add the search icon to the actionbar
            search.setIcon(getResources().getDrawable(R.drawable.ic_search));

            isSearchOpened = false;
        }
        else{
            //open the search entry
            actionbar.setDisplayShowCustomEnabled(true);    //enable it to display a custom view in the actionbar
            actionbar.setCustomView(R.layout.search_bar);   //add the custom view
            actionbar.setDisplayShowTitleEnabled(false);    //hide the title

            input_layout_search = actionbar.getCustomView().findViewById(R.id.input_layout_search);

            input_search = actionbar.getCustomView().findViewById(R.id.input_search);    //the text editor
            input_search.addTextChangedListener(new AutoCompleteTextViewWatcher(this, input_search, getFocusedFragment()));   //the text changed listener

            //listener when clicking on search button
            input_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if(i == EditorInfo.IME_ACTION_SEARCH){
                        Intent intent = new Intent(getApplicationContext(), MovieSearch.class);
                        intent.putExtra("title", input_search.getText().toString().trim());
                        startActivity(intent);

                        return true;
                    }
                    return false;
                }
            });
            input_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), MovieSearch.class);
                    intent.putExtra("title", input_search.getText().toString().trim());
                    startActivity(intent);
                }
            });

            input_search.requestFocus();

            //open the keyboard focused in the input_search
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(input_search, InputMethodManager.SHOW_IMPLICIT);

            //add the close icon
            search.setIcon(getResources().getDrawable(R.drawable.ic_remove_search));

            isSearchOpened = true;
        }
    }


}
