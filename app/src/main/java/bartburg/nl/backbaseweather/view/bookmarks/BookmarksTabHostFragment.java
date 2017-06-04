package bartburg.nl.backbaseweather.view.bookmarks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import bartburg.nl.backbaseweather.R;

/**
 *
 */
public class BookmarksTabHostFragment extends Fragment {

    private static final String OPEN_PAGE = "openTabTag";
    private static final String LIST_TAB_TAG = "listTabTag";
    private static final String MAP_TAB_TAG = "mapTabTag";
    private String openTabTag = LIST_TAB_TAG;
    private FragmentTabHost mTabHost;

    public BookmarksTabHostFragment() {}

    /**
     * @param openPage page to open.
     * @return A new instance of fragment BookmarksTabHostFragment.
     */
    public static BookmarksTabHostFragment newInstance(int openPage) {
        BookmarksTabHostFragment fragment = new BookmarksTabHostFragment();
        Bundle args = new Bundle();
        args.putInt(OPEN_PAGE, openPage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            openTabTag = getArguments().getString(OPEN_PAGE, LIST_TAB_TAG);
        }
    }

    private void initTabHost(View parent) {
        mTabHost = (FragmentTabHost)parent.findViewById(R.id.tab_host_parent);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec(LIST_TAB_TAG).setIndicator("List"),
                BookmarksListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(MAP_TAB_TAG).setIndicator("Map"),
                BookmarksMapFragment.class, null);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mTabHost.getCurrentTab();
            }
        });

        mTabHost.setCurrentTabByTag(openTabTag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_bookmarks_tab_host, container, false);
        initTabHost(parent);
        return parent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(OPEN_PAGE, openTabTag);
    }


}
