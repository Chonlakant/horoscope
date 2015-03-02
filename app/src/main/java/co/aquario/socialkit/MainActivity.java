package co.aquario.socialkit;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import co.aquario.socialkit.event.FailedEvent;
import co.aquario.socialkit.event.SomeEvent;
import co.aquario.socialkit.event.SuccessEvent;
import co.aquario.socialkit.fragment.BaseFragment;
import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.model.SomeData;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
            Snackbar.with(this).text("before post").show(this);
            ApiBus.getInstance().post(new SomeEvent("var1",
                    2));
        }
    }

    @Override public void onResume() {
        super.onResume();
        ApiBus.getInstance().register(this);
    }

    @Override public void onPause() {
        super.onPause();
        ApiBus.getInstance().unregister(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends BaseFragment {

        //@InjectView(R.id.imageView)
        public ImageView randomView;
        public TextView author;

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            randomView = (ImageView) rootView.findViewById(R.id.imageView);
            author = (TextView) rootView.findViewById(R.id.textView);

            randomView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long unixTime = System.currentTimeMillis() / 1000L;
                    int randomTime = (int) unixTime;
                    ApiBus.getInstance().post(new SomeEvent("var1",
                            randomTime));
                }
            });

            //ButterKnife.inject(getActivity(),rootView);
            return rootView;
        }



        @Subscribe
        public void onSomeSuccess(SuccessEvent event) {
            SomeData imageData = event.getSomeResponse();
            Log.e("HEY3!",imageData.src);
            Picasso.with(getActivity()).load(imageData.src+"?fit=crop&fm=jpg&h=480&q=60&w=640").fit().centerInside().into(randomView);
            author.setText(imageData.author);

            ActionBarActivity actionBarActivity = (ActionBarActivity)getActivity();
            ActionBar actionBar = actionBarActivity.getSupportActionBar();

            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(imageData.color)));
            //imageDataList.addAll(event.getImageSearchResponse().getResponseData().getResults());
            //adapter.notifyDataSetChanged();
        }

        @Subscribe
        public void onSomeFailed(FailedEvent event) {
            Snackbar.with(getActivity()).text("Failed to load images").show(getActivity());
        }
    }
}
