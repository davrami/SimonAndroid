package dam2.simon;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fr1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fr1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fr1 extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView imageHelp;
    Button afegir;
    int cont=0;
    TextView ajuda;

    private OnFragmentInteractionListener mListener;


    public fr1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fr1.
     */
    // TODO: Rename and change types and number of parameters
    public static fr1 newInstance(String param1, String param2) {
        fr1 fragment = new fr1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fr1, container, false);
        this.imageHelp = (ImageView) v.findViewById(R.id.imageHelp);
        this.ajuda = (TextView) v.findViewById(R.id.Textajuda);
        this.afegir = (Button) v.findViewById(R.id.afegir);
        afegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cont == 0){
                    imageHelp.setImageResource(R.drawable.img2);
                    ajuda.setText("Van saliendo imagenes, aleatoriamente, y cuando termina,\n" +
                            "tienes que repetir el proceso");
                    cont = 1;
                }else if(cont ==1){
                    afegir.setText("Inici");
                    imageHelp.setImageResource(R.drawable.img3);
                    ajuda.setText("En caso que falles, habrá terminado la partida,\n" +
                            "y se registrará tu puntuación");
                    cont=2;
                }else{
                    afegir.setText("Següent");
                    imageHelp.setImageResource(R.drawable.img1);
                    ajuda.setText("Una vez estas en la pantalla de inicio,\n" +
                            "            para empezar a jugar tienes que hacer clic sobre play");
                    cont=0;
                }
            }
        });
        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
