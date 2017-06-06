package info.nightscout.androidaps.plugins.Careportal;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import info.nightscout.androidaps.MainApp;
import info.nightscout.androidaps.R;
import info.nightscout.androidaps.db.CareportalEvent;
import info.nightscout.androidaps.events.EventCareportalEventChange;
import info.nightscout.androidaps.plugins.Careportal.Dialogs.NewNSTreatmentDialog;

public class CareportalFragment extends Fragment implements View.OnClickListener {

    static CareportalPlugin careportalPlugin;

    TextView iage;
    TextView cage;
    TextView sage;
    TextView pbage;

    static public CareportalPlugin getPlugin() {
        if (careportalPlugin == null) {
            careportalPlugin = new CareportalPlugin();
        }
        return careportalPlugin;
    }

    //                                                    bg,insulin,carbs,prebolus,duration,percent,absolute,profile,split,temptarget
    static final OptionsToShow bgcheck = new OptionsToShow(R.id.careportal_bgcheck, R.string.careportal_bgcheck, true, true, true, false, false, false, false, false, false, false);
    static final OptionsToShow snackbolus = new OptionsToShow(R.id.careportal_snackbolus, R.string.careportal_snackbolus, true, true, true, true, false, false, false, false, false, false);
    static final OptionsToShow mealbolus = new OptionsToShow(R.id.careportal_mealbolus, R.string.careportal_mealbolus, true, true, true, true, false, false, false, false, false, false);
    static final OptionsToShow correctionbolus = new OptionsToShow(R.id.careportal_correctionbolus, R.string.careportal_correctionbolus, true, true, true, true, false, false, false, false, false, false);
    static final OptionsToShow carbcorrection = new OptionsToShow(R.id.careportal_carbscorrection, R.string.careportal_carbscorrection, true, false, true, false, false, false, false, false, false, false);
    static final OptionsToShow combobolus = new OptionsToShow(R.id.careportal_combobolus, R.string.careportal_combobolus, true, true, true, true, true, false, false, false, true, false);
    static final OptionsToShow announcement = new OptionsToShow(R.id.careportal_announcement, R.string.careportal_announcement, true, false, false, false, false, false, false, false, false, false);
    static final OptionsToShow note = new OptionsToShow(R.id.careportal_note, R.string.careportal_note, true, false, false, false, true, false, false, false, false, false);
    static final OptionsToShow question = new OptionsToShow(R.id.careportal_question, R.string.careportal_question, true, false, false, false, false, false, false, false, false, false);
    static final OptionsToShow exercise = new OptionsToShow(R.id.careportal_exercise, R.string.careportal_exercise, false, false, false, false, true, false, false, false, false, false);
    static final OptionsToShow sitechange = new OptionsToShow(R.id.careportal_pumpsitechange, R.string.careportal_pumpsitechange, true, true, false, false, false, false, false, false, false, false);
    static final OptionsToShow sensorstart = new OptionsToShow(R.id.careportal_cgmsensorstart, R.string.careportal_cgmsensorstart, true, false, false, false, false, false, false, false, false, false);
    static final OptionsToShow sensorchange = new OptionsToShow(R.id.careportal_cgmsensorinsert, R.string.careportal_cgmsensorinsert, true, false, false, false, false, false, false, false, false, false);
    static final OptionsToShow insulinchange = new OptionsToShow(R.id.careportal_insulincartridgechange, R.string.careportal_insulincartridgechange, true, false, false, false, false, false, false, false, false, false);
    static final OptionsToShow pumpbatterychange = new OptionsToShow(R.id.careportal_pumpbatterychange, R.string.careportal_pumpbatterychange, true, false, false, false, false, false, false, false, false, false);
    static final OptionsToShow tempbasalstart = new OptionsToShow(R.id.careportal_tempbasalstart, R.string.careportal_tempbasalstart, true, false, false, false, true, true, true, false, false, false);
    static final OptionsToShow tempbasalend = new OptionsToShow(R.id.careportal_tempbasalend, R.string.careportal_tempbasalend, true, false, false, false, false, false, false, false, false, false);
    static final OptionsToShow profileswitch = new OptionsToShow(R.id.careportal_profileswitch, R.string.careportal_profileswitch, true, false, false, false, true, false, false, true, false, false);
    static final OptionsToShow openapsoffline = new OptionsToShow(R.id.careportal_openapsoffline, R.string.careportal_openapsoffline, false, false, false, false, true, false, false, false, false, false);
    static final OptionsToShow temptarget = new OptionsToShow(R.id.careportal_temporarytarget, R.string.careportal_temporarytarget, false, false, false, false, true, false, false, false, false, true);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.careportal_fragment, container, false);

        view.findViewById(R.id.careportal_bgcheck).setOnClickListener(this);
        view.findViewById(R.id.careportal_announcement).setOnClickListener(this);
        view.findViewById(R.id.careportal_cgmsensorinsert).setOnClickListener(this);
        view.findViewById(R.id.careportal_cgmsensorstart).setOnClickListener(this);
        view.findViewById(R.id.careportal_combobolus).setOnClickListener(this);
        view.findViewById(R.id.careportal_correctionbolus).setOnClickListener(this);
        view.findViewById(R.id.careportal_carbscorrection).setOnClickListener(this);
        view.findViewById(R.id.careportal_exercise).setOnClickListener(this);
        view.findViewById(R.id.careportal_insulincartridgechange).setOnClickListener(this);
        view.findViewById(R.id.careportal_pumpbatterychange).setOnClickListener(this);
        view.findViewById(R.id.careportal_mealbolus).setOnClickListener(this);
        view.findViewById(R.id.careportal_note).setOnClickListener(this);
        view.findViewById(R.id.careportal_profileswitch).setOnClickListener(this);
        view.findViewById(R.id.careportal_pumpsitechange).setOnClickListener(this);
        view.findViewById(R.id.careportal_question).setOnClickListener(this);
        view.findViewById(R.id.careportal_snackbolus).setOnClickListener(this);
        view.findViewById(R.id.careportal_tempbasalend).setOnClickListener(this);
        view.findViewById(R.id.careportal_tempbasalstart).setOnClickListener(this);
        view.findViewById(R.id.careportal_openapsoffline).setOnClickListener(this);
        view.findViewById(R.id.careportal_temporarytarget).setOnClickListener(this);

        iage = (TextView) view.findViewById(R.id.careportal_insulinage);
        cage = (TextView) view.findViewById(R.id.careportal_canulaage);
        sage = (TextView) view.findViewById(R.id.careportal_sensorage);
        pbage = (TextView) view.findViewById(R.id.careportal_pbage);

        updateGUI();
        return view;
    }

    @Override
    public void onClick(View view) {
        action(view.getId(), getFragmentManager());
    }

    public static void action(int id, FragmentManager manager) {
        NewNSTreatmentDialog newDialog = new NewNSTreatmentDialog();
        switch (id) {
            case R.id.careportal_bgcheck:
                newDialog.setOptions(bgcheck);
                break;
            case R.id.careportal_announcement:
                newDialog.setOptions(announcement);
                break;
            case R.id.careportal_cgmsensorinsert:
                newDialog.setOptions(sensorchange);
                break;
            case R.id.careportal_cgmsensorstart:
                newDialog.setOptions(sensorstart);
                break;
            case R.id.careportal_combobolus:
                newDialog.setOptions(combobolus);
                break;
            case R.id.careportal_correctionbolus:
                newDialog.setOptions(correctionbolus);
                break;
            case R.id.careportal_carbscorrection:
                newDialog.setOptions(carbcorrection);
                break;
            case R.id.careportal_exercise:
                newDialog.setOptions(exercise);
                break;
            case R.id.careportal_insulincartridgechange:
                newDialog.setOptions(insulinchange);
                break;
            case R.id.careportal_pumpbatterychange:
                newDialog.setOptions(pumpbatterychange);
                break;
            case R.id.careportal_mealbolus:
                newDialog.setOptions(mealbolus);
                break;
            case R.id.careportal_note:
                newDialog.setOptions(note);
                break;
            case R.id.careportal_profileswitch:
                newDialog.setOptions(profileswitch);
                break;
            case R.id.careportal_pumpsitechange:
                newDialog.setOptions(sitechange);
                break;
            case R.id.careportal_question:
                newDialog.setOptions(question);
                break;
            case R.id.careportal_snackbolus:
                newDialog.setOptions(snackbolus);
                break;
            case R.id.careportal_tempbasalstart:
                newDialog.setOptions(tempbasalstart);
                break;
            case R.id.careportal_tempbasalend:
                newDialog.setOptions(tempbasalend);
                break;
            case R.id.careportal_openapsoffline:
                newDialog.setOptions(openapsoffline);
                break;
            case R.id.careportal_temporarytarget:
                newDialog.setOptions(temptarget);
                break;
            default:
                newDialog = null;
        }
        if (newDialog != null)
            newDialog.show(manager, "NewNSTreatmentDialog");
    }

    @Override
    public void onPause() {
        super.onPause();
        MainApp.bus().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainApp.bus().register(this);
    }

    @Subscribe
    public void onStatusEvent(final EventCareportalEventChange c) {
        updateGUI();
    }

    void updateGUI() {
        Activity activity = getActivity();
        updateAge(activity, sage, iage, cage, pbage);
    }

    public static void updateAge(Activity activity, final TextView sage, final TextView iage, final TextView cage, final TextView pbage) {
        if (activity != null) {
            activity.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            CareportalEvent careportalEvent;
                            if (sage != null) {
                                careportalEvent = MainApp.getDbHelper().getLastCareportalEvent(CareportalEvent.SENSORCHANGE);
                                sage.setText(careportalEvent != null ? careportalEvent.age() : MainApp.sResources.getString(R.string.notavailable));
                            }
                            if (iage != null) {
                                careportalEvent = MainApp.getDbHelper().getLastCareportalEvent(CareportalEvent.INSULINCHANGE);
                                iage.setText(careportalEvent != null ? careportalEvent.age() : MainApp.sResources.getString(R.string.notavailable));
                            }
                            if (cage != null) {
                                careportalEvent = MainApp.getDbHelper().getLastCareportalEvent(CareportalEvent.SITECHANGE);
                                cage.setText(careportalEvent != null ? careportalEvent.age() : MainApp.sResources.getString(R.string.notavailable));
                            }
                            if (pbage != null) {
                                careportalEvent = MainApp.getDbHelper().getLastCareportalEvent(CareportalEvent.PUMPBATTERYCHANGE);
                                pbage.setText(careportalEvent != null ? careportalEvent.age() : MainApp.sResources.getString(R.string.notavailable));
                            }
                        }
                    }
            );
        }
    }

}
