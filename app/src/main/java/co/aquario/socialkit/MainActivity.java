package co.aquario.socialkit;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.model.interfaces.OnCheckedChangeListener;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import co.aquario.socialkit.custom.CustomPrimaryDrawerItem;
import co.aquario.socialkit.event.FailedEvent;
import co.aquario.socialkit.event.SomeEvent;
import co.aquario.socialkit.event.SuccessEvent;
import co.aquario.socialkit.fragment.BaseFragment;
import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.model.SomeData;


public class MainActivity extends AppCompatActivity {

    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private Toolbar toolbar;
    private IProfile profile;

    Bundle savedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedState = savedInstanceState;
        setContentView(R.layout.activity_main);
        initToolbar();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
            ApiBus.getInstance().post(new SomeEvent("var1",
                    2));
        }


    }

    @Subscribe
    public void onSomeSuccess(SuccessEvent event) {
        SomeData imageData = event.getSomeResponse();
        Log.e("HEY3!", imageData.src);

        buildHeader(true, savedState);
        buildDrawer(savedState);

        profile = new ProfileDrawerItem().withName(imageData.author)
                .withEmail("iamnotkorr@gmail.com")
                .withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460")
                .withIdentifier(0);

        headerResult.setActiveProfile(profile);
        //headerResult.getProfiles().get(0).setName(imageData.author);

        Picasso.with(this).load(imageData.src + "?fit=crop&fm=jpg&h=480&q=60&w=640").fit().centerCrop().into(headerResult.getHeaderBackgroundView());
        result.setSelectionByIdentifier(5, false);

    }


    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.cover)
                .withCompactStyle(compact)

                .addProfiles(
                        //profile,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem()
                                .withName(getResources().getString(R.string.action_change_avatar))
                                .withIcon(GoogleMaterial.Icon.gmd_settings)
                                .withDescription("Upload or take new picture")
                                        //.withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text))
                                .withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem()
                                .withName(getResources().getString(R.string.action_change_cover))
                                .withIcon(GoogleMaterial.Icon.gmd_settings)
                )


                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {

                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })

                .withSavedInstance(savedInstanceState)
                .build();
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void buildDrawer(Bundle savedInstanceState) {

        CustomPrimaryDrawerItem menuHeader = new CustomPrimaryDrawerItem();

    /*
        ImageView channelMenu = (ImageView) menuHeader.getMenuHeader().findViewById(R.id.channel_menu);
        ImageView sociallMenu = (ImageView) menuHeader.getMenuHeader().findViewById(R.id.social_menu);
        ImageView videoMenu = (ImageView) menuHeader.getMenuHeader().findViewById(R.id.video_menu);
        ImageView photoMenu = (ImageView) menuHeader.getMenuHeader().findViewById(R.id.photo_menu);

        channelMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        */

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        menuHeader
                        //new DividerDrawerItem(),
                        //new ToggleDrawerItem().withName("Toggle").withIcon(R.drawable.tattoo_set_0_0).withChecked(false).withOnCheckedChangeListener(onCheckedChangeListener),
                        //new PrimaryDrawerItem().withName(R.string.hello_world).withIcon(FontAwesome.Icon.faw_home),
                        //here we use a customPrimaryDrawerItem we defined in our sample app
                        //this custom DrawerItem extends the PrimaryDrawerItem so it just overwrites some methods
                        ,

                        new SectionDrawerItem().withName("Menu"),
                        new SecondaryDrawerItem().withName("Home").withIcon(FontAwesome.Icon.faw_home),
                        new SecondaryDrawerItem().withName("Live History").withIcon(FontAwesome.Icon.faw_history),
                        new SecondaryDrawerItem().withName("Setting").withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName("Maxpoint").withIcon(FontAwesome.Icon.faw_btc),
                        new SecondaryDrawerItem().withName("Tattoo Store").withIcon(FontAwesome.Icon.faw_shopping_cart).setEnabled(false),
                        new SecondaryDrawerItem().withName("Term & Policies").withIcon(FontAwesome.Icon.faw_terminal)

                ) // add the items we want to use with our Drawer
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity
                        MainActivity.this.finish();
                        //return true if we have consumed the event
                        return true;
                    }
                })
                .addStickyDrawerItems(
                        new SwitchDrawerItem().withName(R.string.action_notification).withIcon(R.drawable.tattoo_set_0_0).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new SecondaryDrawerItem().withName(R.string.action_logout).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(10)
                )
                .withAnimateDrawerItems(true)
                .withSavedInstance(savedInstanceState)
                .build();
    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (drawerItem instanceof Nameable) {
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            } else {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ApiBus.getInstance().register(this);
    }

    @Override
    public void onPause() {
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
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        View rootLayout;

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
            this.rootLayout = rootView;
            return rootView;
        }


        @Subscribe
        public void onSomeSuccess(SuccessEvent event) {
            SomeData imageData = event.getSomeResponse();
            Log.e("HEY3!", imageData.src);
            Picasso.with(getActivity()).load(imageData.src + "?fit=crop&fm=jpg&h=480&q=60&w=640").fit().centerCrop().into(randomView);
            author.setText(imageData.author);

            //AppCompatActivity actionBarActivity = (AppCompatActivity) getActivity();
            //ActionBar actionBar = actionBarActivity.getSupportActionBar();

            //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(imageData.color)));
            //imageDataList.addAll(event.getImageSearchResponse().getResponseData().getResults());
            //adapter.notifyDataSetChanged();

            Snackbar.make(rootLayout, "Image fetched!", Snackbar.LENGTH_LONG)
                    .setAction("NEXT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            long unixTime = System.currentTimeMillis() / 1000L;
                            int randomTime = (int) unixTime;
                            ApiBus.getInstance().post(new SomeEvent("var1",
                                    randomTime));
                        }
                    })
                    .show();
        }

        @Subscribe
        public void onSomeFailed(FailedEvent event) {
            Snackbar.make(rootLayout, "Image failed", Snackbar.LENGTH_SHORT)
                    .setAction("TRY AGAIN", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            long unixTime = System.currentTimeMillis() / 1000L;
                            int randomTime = (int) unixTime;
                            ApiBus.getInstance().post(new SomeEvent("var1",
                                    randomTime));
                        }
                    })
                    .show();
        }
    }
}
