package evento.example.com.evento;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;







public class DisplayList extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        GloVar.colList=new ArrayList<>();
        GloVar.colSpinner=new ArrayList<>();
        GloVar.eventSpinner=new ArrayList<>();
        GloVar.deptSpinner=new ArrayList<>();
        GloVar.categorySpinner=new ArrayList<>();
        Toast.makeText(getApplicationContext(),"on create",Toast.LENGTH_LONG).show();



        FirebaseDatabase database=FirebaseDatabase.getInstance();
        Toast.makeText(getApplicationContext(),"initialization done",Toast.LENGTH_LONG).show();

        GloVar.collegeUpvoted=new ArrayList<String>();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        GloVar.uid= firebaseUser.getUid();
        DatabaseReference ref2=database.getReference().child("users/"+GloVar.uid+"/vote/college");

        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnapshot:dataSnapshot.getChildren())
                {
                    if(Integer.parseInt(childSnapshot.getValue().toString())==1)
                    {
                        GloVar.collegeUpvoted.add(childSnapshot.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        DatabaseReference ref=database.getReference().child("events/college-events/competitions");
        DatabaseReference ref1=database.getReference().child("events/college-events/cat-names");



        Toast.makeText(getApplicationContext(),"before l1",Toast.LENGTH_LONG).show();

        ref1.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GloVar.categorySpinner.clear();

                Toast.makeText(getApplicationContext(),"Listener 1 on",Toast.LENGTH_LONG).show();

                for(DataSnapshot childSnap:dataSnapshot.getChildren())
                {
                    if(Integer.parseInt(childSnap.getValue().toString())==1)
                    {
                        GloVar.categorySpinner.add(childSnap.getKey());
                    }
                }

                Toast.makeText(getApplicationContext(),"Listener 1 off",Toast.LENGTH_LONG).show();

                System.out.println("Not working:    cat list updated");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"dbs error",Toast.LENGTH_LONG).show();

            }
        });
        Toast.makeText(getApplicationContext(),"1 ends",Toast.LENGTH_LONG).show();


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Toast.makeText(getApplicationContext(),"Listener 2 on",Toast.LENGTH_LONG).show();

                GloVar.colList.clear();
                GloVar.colSpinner.clear();
                GloVar.eventSpinner.clear();
                GloVar.deptSpinner.clear();

                for(DataSnapshot childSnaps:dataSnapshot.getChildren()){
                    CollegeItem ci=childSnaps.getValue(CollegeItem.class);
                    String college=ci.getCollege(),event=ci.getEvent(),dept=ci.getDept();
                    ci.setKey(childSnaps.getKey());
                    GloVar.colList.add(ci);
                    if(!GloVar.colSpinner.contains(college))
                        GloVar.colSpinner.add(ci.getCollege());
                    if(!GloVar.eventSpinner.contains(event))
                        GloVar.eventSpinner.add(ci.getEvent());
                    if(!GloVar.deptSpinner.contains(dept))
                        GloVar.deptSpinner.add(ci.getDept());
                }
                System.out.println("Not working:    all lists updated");

                Toast.makeText(getApplicationContext(),"Listener 2 off",Toast.LENGTH_LONG).show();

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                // Create the adapter that will return a fragment for each of the three
                // primary sections of the activity.
                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                // Set up the ViewPager with the sections adapter.
                mViewPager = (ViewPager) findViewById(R.id.container);

                mViewPager.setAdapter(mSectionsPagerAdapter);

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(mViewPager);

                System.out.println("Tab layout added:    cat list updated");

                Toast.makeText(getApplicationContext(),"Tabs added",Toast.LENGTH_LONG).show();





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Toast.makeText(getApplicationContext(),"2 ends",Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_display_list, container, false);
            RecyclerView r1 = (RecyclerView) rootView.findViewById(R.id.recycler1);


            List<String> spinnerList=new ArrayList<>();
            Spinner s1=(Spinner) rootView.findViewById(R.id.cat_name);

            final int id= getArguments().getInt(ARG_SECTION_NUMBER);

            final ArrayList<CollegeItem> data=new ArrayList<>();

            FirebaseDatabase database=FirebaseDatabase.getInstance();
            final DatabaseReference ref=database.getReference().child("events/college-events");
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            r1.setLayoutManager(mLayoutManager);
            final CollegeAdapter collegeAdapter = new CollegeAdapter(data,getActivity());
            r1.setAdapter(collegeAdapter);
            String change1; String change2;
            ArrayAdapter<String> dataAdapter;
            s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id1) {

                    String change2=parent.getItemAtPosition(position).toString();
                    String change1="";
                    switch(id)
                    {
                        case 1:
                            change1="colleges";
                            break;
                        case 2:
                            change1="departments";
                            break;
                        case 3:
                            change1="events";
                            break;
                        case 4:
                            change1="categories";
                            break;
                        default:
                            break;
                    }
                    collegeListener(change1,change2,ref, data, collegeAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            switch(id)
            {
                case 1:
                    dataAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, GloVar.colSpinner);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s1.setAdapter(dataAdapter);

                    change1="colleges";
                    change2=s1.getSelectedItem().toString();
                    System.out.println(change2);
                    break;
                case 2:
                    dataAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, GloVar.deptSpinner);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s1.setAdapter(dataAdapter);

                    change1="departments";
                    change2=s1.getSelectedItem().toString();
                    break;
                case 3:
                    dataAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, GloVar.eventSpinner);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s1.setAdapter(dataAdapter);

                    change1="events";
                    change2=s1.getSelectedItem().toString();
                    break;
                case 4:
                    dataAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, GloVar.categorySpinner);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s1.setAdapter(dataAdapter);

                    change1="categories";
                    change2=s1.getSelectedItem().toString();
                    break;
                default:
                    change1="categories";
                    change2="sports";
                    break;
            }
            try {
                collegeListener(change1,change2,ref, data, collegeAdapter);
            }
            catch (Exception e)
            {
                System.out.println("Exception:"+e.toString());
            }


            return rootView;
        }

        private void collegeListener(String change1,String change2,DatabaseReference ref, final ArrayList<CollegeItem> data, final CollegeAdapter collegeAdapter ) {

            ref.child(change1).child(change2).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<String> str=new ArrayList<String>();
                    data.clear();
                    for(DataSnapshot childsnapshot : dataSnapshot.getChildren())
                    {
                        str.add(childsnapshot.getValue().toString());
                    }
                   Iterator i=str.iterator();
                    int i1=0;
                    while(i.hasNext())
                    {
                        String current=i.next().toString();
                        while(i1 < GloVar.colList.size())
                        {
                            CollegeItem ci=GloVar.colList.get(i1);
                            if(ci.getKey().equals(current)) {
                                data.add(GloVar.colList.get(i1));
                                i1++;
                                break;
                            }
                            i1++;
                        }

                    }
                    collegeAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Colleges";
                case 1:
                    return "Departments";
                case 2:
                    return "Events";
                case 3:
                    return "Categories";

            }
            return null;
        }
    }
}
