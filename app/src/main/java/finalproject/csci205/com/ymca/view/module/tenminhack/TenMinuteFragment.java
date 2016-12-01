package finalproject.csci205.com.ymca.view.module.tenminhack;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import finalproject.csci205.com.ymca.R;



public class TenMinuteFragment extends Fragment implements View.OnClickListener {


    /**
     * Required empty constructor
     */
    public TenMinuteFragment() {
    }

    /**
     * Use this factor method to create a new instance of {@link TenMinuteFragment}
     *
     * @return A new instance of {@link TenMinuteFragment}
     */
    public static TenMinuteFragment newInstance() {
        TenMinuteFragment fragment = new TenMinuteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Ten Minute Hack");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tenmin, container, false);
        return root;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
