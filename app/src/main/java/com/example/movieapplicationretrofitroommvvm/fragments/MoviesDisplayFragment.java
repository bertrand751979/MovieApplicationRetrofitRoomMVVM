package com.example.movieapplicationretrofitroommvvm.fragments;

import static com.example.movieapplicationretrofitroommvvm.activities.SearchMovieActivity.MOVIE_EXTRA;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapplicationretrofitroommvvm.InterfaceEditTextDialog;
import com.example.movieapplicationretrofitroommvvm.OnLinearClickedAction;
import com.example.movieapplicationretrofitroommvvm.R;
import com.example.movieapplicationretrofitroommvvm.activities.DetailActivity;
import com.example.movieapplicationretrofitroommvvm.adapters.MovieAdapter;
import com.example.movieapplicationretrofitroommvvm.model.Result;
import com.example.movieapplicationretrofitroommvvm.model.Root;
import com.example.movieapplicationretrofitroommvvm.repository.RepositoryMovie;
import com.example.movieapplicationretrofitroommvvm.viewModels.MovieDisplayFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class MoviesDisplayFragment extends Fragment {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Result> test = new ArrayList<>();
    public MovieDisplayFragmentViewModel movieDisplayFragmentViewModel;
    public MenuItem itemYear;
    public  MenuItem itemMenu;
    private InterfaceEditTextDialog interfaceEditTextDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDisplayFragmentViewModel = new ViewModelProvider(this).get(MovieDisplayFragmentViewModel.class);
        getActivity().setTitle("Films: ");
        test= RepositoryMovie.getInstance().getListMovie();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_display_movie,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_movie);
        OnLinearClickedAction onLinearClickedAction = new OnLinearClickedAction() {
            @Override
            public void goToDescription(Result result) {
                Intent intent = new Intent(MoviesDisplayFragment.this.getContext(), DetailActivity.class);
                intent.putExtra(MOVIE_EXTRA,result);
                startActivity(intent);
                Toast.makeText(MoviesDisplayFragment.this.getContext(), "Vers Description", Toast.LENGTH_SHORT).show();
            }
        };

        interfaceEditTextDialog = new InterfaceEditTextDialog() {
            @Override
            public void transfertEditText(String searchText) {
                RepositoryMovie.getInstance().mySearchList.clear();
                if(RepositoryMovie.getInstance().choiceYear(searchText)){
                    movieAdapter.setListAdapter(RepositoryMovie.getInstance().mySearchList);
                }else{
                    movieAdapter.setListAdapter(RepositoryMovie.getInstance().listMovie);
                }
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        movieAdapter = new MovieAdapter(onLinearClickedAction);
        recyclerView.setAdapter(movieAdapter);
        movieDisplayFragmentViewModel.movieLiveData.observe(getViewLifecycleOwner(), new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                movieAdapter.setListAdapter(new ArrayList<>(results));
            }
        });
        movieDisplayFragmentViewModel.toPostMyItemsList();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        itemMenu=menu.findItem(R.id.menu_icon);
        itemYear = menu.findItem(R.id.search_year);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_icon:
                return (true);

            case R.id.search_year:
                showAlertDialog();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }
    private void showAlertDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        MyAlertDialogFragment alertDialog = MyAlertDialogFragment.newInstance("Some title");
        alertDialog.setInterfaceEditTextDialog(interfaceEditTextDialog);
        alertDialog.show(fm, "fragment_alert");
    }


}
