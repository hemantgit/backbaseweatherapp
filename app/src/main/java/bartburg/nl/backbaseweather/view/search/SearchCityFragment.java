package bartburg.nl.backbaseweather.view.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import bartburg.nl.backbaseweather.R;
import bartburg.nl.backbaseweather.view.bookmarks.OnBookmarkInteractionListener;
import bartburg.nl.backbaseweather.view.search.data.SearchableCitiesContainer;

/**
 *
 */
public class SearchCityFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int columnCount = 1;
    private OnBookmarkInteractionListener listener;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchCityFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchCityFragment newInstance(int columnCount) {
        SearchCityFragment fragment = new SearchCityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_city_search_result_list, container, false);
        View list = parent.findViewById(R.id.searchable_cities_list);
        EditText searchField = (EditText) parent.findViewById(R.id.search_field_edittext);
        searchField.addTextChangedListener(getSearchFieldChangedWatcher());
        if (list instanceof RecyclerView) {
            Context context = parent.getContext();
            recyclerView = (RecyclerView) list;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }
            recyclerView.setAdapter(new CitySearchResultViewAdapter(SearchableCitiesContainer.CITIES, listener));
        }
        return parent;
    }

    @NonNull
    private TextWatcher getSearchFieldChangedWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchString = s.toString();
                if (searchString.length() > 0) {
                    ArrayList<String> cityNames = SearchableCitiesContainer.searchCities(searchString);
                    recyclerView.swapAdapter(new CitySearchResultViewAdapter(cityNames, listener), true);
                } else {
                    recyclerView.setAdapter(new CitySearchResultViewAdapter(SearchableCitiesContainer.CITIES, listener));
                }
            }
        };
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBookmarkInteractionListener) {
            listener = (OnBookmarkInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBookmarkInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
