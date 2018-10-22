package boiz.coolen.die.splitwizer.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import boiz.coolen.die.splitwizer.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    @BindView(R.id.penny)
    RadioButton penny;
    @BindView(R.id.dm)
    RadioButton dm;
    @BindView(R.id.veschber)
    RadioButton veschber;
    @BindView(R.id.hf)
    RadioButton hf;
    @BindView(R.id.sonstigesrb)
    RadioButton sonstigesrb;
    @BindView(R.id.sonstigeset)
    EditText sonstigesET;
    @BindView(R.id.betrag)
    EditText betrag;
    @BindView(R.id.domi)
    ToggleButton domi;
    @BindView(R.id.franzi)
    ToggleButton franzi;
    @BindView(R.id.leon)
    ToggleButton leon;
    @BindView(R.id.domi1)
    AppCompatCheckBox domi1;
    @BindView(R.id.franzi1)
    AppCompatCheckBox franzi1;
    @BindView(R.id.leon1)
    AppCompatCheckBox leon1;
    @BindView(R.id.aufgehts)
    Button aufGehts;


    private RadioButton[] radioButtons;
    private ToggleButton[] toggleButtons;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);
        radioButtons = new RadioButton[]{penny, dm, veschber, hf, sonstigesrb};
        toggleButtons = new ToggleButton[]{domi, franzi, leon};

        // Radio Buttons
        penny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(penny);
            }
        });
        dm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(dm);
            }
        });
        veschber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(veschber);
            }
        });
        hf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(hf);
            }
        });
        sonstigesrb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(sonstigesrb);
            }
        });

        // Toggle Bois

        domi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleGroup(domi);
            }
        });

        leon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleGroup(leon);
            }
        });

        franzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleGroup(franzi);
            }
        });

        // Normal Buttons

        aufGehts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payerNotSelected()) {
                    System.out.println("Bisch du dumm oda sooo?");
                    return;
                }
                if (customDescriptionSelectedButNotFilledOut()) {
                    System.out.println("Wie dumm bisch du eigentlich?");
                    return;
                }
                if (sumNotEntered()) {
                    System.out.println("Wie dumm bisch du eigentlich?");
                    return;
                }

                String description = getDescription();
                Double sum = Double.valueOf(betrag.getText().toString());
                String payer = getPayer();
                List<String> participants = getParticipants();
            }
        });

        return view;
    }

    private String getDescription() {
        for (RadioButton radioButton : radioButtons) {
            if (radioButton.isSelected()) {
                if (radioButton.equals(sonstigesrb)) {
                    return sonstigesET.getText().toString();
                }
                return radioButton.getText().toString();
            }
        }
        throw new IllegalStateException("LUL");
    }

    private List<String> getParticipants() {
        List<String> participants = new ArrayList<>();
        if (domi1.isChecked()) participants.add(domi1.getText().toString());
        if (franzi1.isChecked()) participants.add(franzi1.getText().toString());
        if (leon1.isChecked()) participants.add(leon1.getText().toString());

        return participants;
    }

    private String getPayer() {
        for (ToggleButton toggleButton : toggleButtons) {
            if (toggleButton.isChecked()) {
                return toggleButton.getTextOn().toString();
            }
        }
        throw new IllegalStateException("Was isch des?");
    }

    private boolean sumNotEntered() {
        return betrag.getText().toString().isEmpty();
    }

    private boolean customDescriptionSelectedButNotFilledOut() {
        return sonstigesrb.isSelected() && sonstigesET.getText().toString().isEmpty();
    }

    private boolean payerNotSelected() {
        for (ToggleButton toggleButton : toggleButtons) {
            if (toggleButton.isChecked()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setSelected(RadioButton button) {
        for (RadioButton radioButton : radioButtons) {
            if (radioButton != button) {
                radioButton.setChecked(false);
            }
        }
    }

    private void toggleGroup(ToggleButton button) {
        if (!button.isChecked())  button.setChecked(true);
        for (ToggleButton toggleButton : toggleButtons) {
            if (toggleButton != button) {
                toggleButton.setChecked(false);
            }
        }
    }
}
