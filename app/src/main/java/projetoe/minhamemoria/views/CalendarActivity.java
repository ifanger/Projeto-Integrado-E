package projetoe.minhamemoria.views;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;

import projetoe.minhamemoria.R;
import projetoe.minhamemoria.controllers.CalendarItemController;
import projetoe.minhamemoria.models.CalendarItem;
import projetoe.minhamemoria.views.adapters.CalendarItemAdapter;

public class CalendarActivity extends AppCompatActivity {
    MaterialCalendarView calendarView;
    CalendarItemController controller;
    FloatingActionButton btnAddMarker;
    List<CalendarItem> calendarItems;
    RecyclerView calendarItemsList;
    CalendarItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (MaterialCalendarView) findViewById(R.id.calendar);
        calendarItemsList = (RecyclerView) findViewById(R.id.list_calendar);
        btnAddMarker = (FloatingActionButton) findViewById(R.id.button_add_event);

        new Thread(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
        init();
    }

    private void init() {
        try {
            controller = new CalendarItemController(this);
            calendarItems = controller.getAlarms();

            adapter = new CalendarItemAdapter(calendarItems, this, controller);
            RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false);

            calendarItemsList.setHasFixedSize(true);
            calendarItemsList.setNestedScrollingEnabled(false);
            calendarItemsList.setLayoutManager(layout);
            calendarItemsList.setAdapter(adapter);

            loadCalendar();
            adapter.notifyDataSetChanged();

            btnAddMarker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addNewMarker();
                }
            });
        } catch(Exception e) {
            handleException(e);
        } catch (CalendarItem.TimeException e) {
            handleException(e);
        } catch (CalendarItem.DateException e) {
            handleException(e);
        } catch (CalendarItem.NameException e) {
            handleException(e);
        }
    }

    private void loadCalendar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(CalendarItem calendarItem : calendarItems) {
                    addMarkerToCalendar(calendarItem.getDay(), calendarItem.getMonth(), calendarItem.getYear());
                }
            }
        }).start();
    }

    private void handleException(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }

    private void addMarkerToCalendar(final int eventDay, final int eventMonth, final int eventYear) {
        DayViewDecorator decorator = new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return (day.getDay() == eventDay) &&
                        (day.getMonth() == eventMonth - 1) &&
                        (day.getYear() == eventYear);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(20, getResources().getColor(R.color.colorPrimary)));
            }
        };

        calendarView.addDecorator(decorator);
    }

    private void addCalendarItem(CalendarItem calendarItem) {
        controller.insert(calendarItem);
        calendarItems.add(calendarItem);
        addMarkerToCalendar(calendarItem.getDay(), calendarItem.getMonth(), calendarItem.getYear());
        adapter.notifyDataSetChanged();
    }

    private void addNewMarker() {
        final View layout = getLayoutInflater().inflate(R.layout.dialog_new_calendar_item, null);
        final TextInputLayout markerTitle = layout.findViewById(R.id.text_layout_calendar_title);
        final Button markerDate = layout.findViewById(R.id.btn_set_date);
        final Button markerTime = layout.findViewById(R.id.btn_set_time);
        final Calendar now = Calendar.getInstance();

        markerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                StringBuilder builder = new StringBuilder();
                                builder.append(String.format("%04d-", year));
                                builder.append(String.format("%02d-", monthOfYear + 1));
                                builder.append(String.format("%02d", dayOfMonth));
                                markerDate.setText(builder.toString());
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.show(getFragmentManager(), "datePickerDialog");
            }
        });

        markerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                StringBuilder builder = new StringBuilder();
                                builder.append(String.format("%02d:", hourOfDay));
                                builder.append(String.format("%02d", minute));
                                markerTime.setText(builder.toString());
                            }
                        },
                        now.get(Calendar.HOUR),
                        now.get(Calendar.MINUTE),
                        true);

                timePickerDialog.show(getFragmentManager(), "timePickerDialog");
            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.str_new_calendar_item)
                .setView(layout)
                .setPositiveButton(R.string.str_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(markerTitle.getEditText() == null)
                            return;

                        try {
                            CalendarItem calendarItem = new CalendarItem(
                                    markerTitle.getEditText().getText().toString(),
                                    markerDate.getText().toString(),
                                    markerTime.getText().toString());

                            addCalendarItem(calendarItem);
                        } catch (CalendarItem.NameException e) {
                            handleException(e);
                        } catch (CalendarItem.DateException e) {
                            handleException(e);
                        } catch (CalendarItem.TimeException e) {
                            handleException(e);
                        }
                    }
                })
                .setNeutralButton(R.string.str_cancel, null)
                .create();
        alertDialog.show();
    }
}
