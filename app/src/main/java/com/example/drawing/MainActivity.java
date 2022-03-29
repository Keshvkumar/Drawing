package com.example.drawing;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.slider.RangeSlider;

import yuku.ambilwarna.AmbilWarnaDialog;
import com.google.android.material.slider.RangeSlider;

public class MainActivity extends AppCompatActivity
{

    private PaintView paintView;
    ImageView colourPicker, penPicker, eraserPicker, sharePicker, resetBoard;
    // creating a RangeSlider object, which will
    // help in selecting the width of the Stroke
    private RangeSlider rangeSlider;
    int defaultcolor, currentWidth, currentColor;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            paintView = findViewById(R.id.paint_view);
            colourPicker = findViewById(R.id.colour_picker);
            penPicker = findViewById(R.id.pen_picker);
            eraserPicker = findViewById(R.id.eraser_picker);
            sharePicker = findViewById(R.id.image_share);
            resetBoard = findViewById(R.id.clear_all_picker);
            rangeSlider = findViewById(R.id.width_bar);
            defaultcolor = android.R.color.black;
            currentWidth = 10;


            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            paintView.init(metrics,currentWidth);


            // set the range of the RangeSlider
            rangeSlider.setValueFrom(0.0f);
            rangeSlider.setValueTo(500.0f);

            rangeSlider.setValues(Float.parseFloat(String.valueOf(currentWidth)));

            // the button will toggle the visibility of the RangeBar/RangeSlider for pen stroke width
            penPicker.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    if (rangeSlider.getVisibility() == View.VISIBLE)
                        rangeSlider.setVisibility(View.GONE);
                    else
                        rangeSlider.setVisibility(View.VISIBLE);
                    return true;
                }
            });

            // the button will toggle the visibility of the RangeBar/RangeSlider for eraser stroke width
            eraserPicker.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    if (rangeSlider.getVisibility() == View.VISIBLE)
                        rangeSlider.setVisibility(View.GONE);
                    else
                        rangeSlider.setVisibility(View.VISIBLE);
                    return true;
                }
            });


            // adding a OnChangeListener which will
            // change the stroke width
            // as soon as the user slides the slider
            rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                    currentWidth = (int) value;
                    rangeSlider.setValues(Float.parseFloat(String.valueOf(currentWidth)));
                    paintView.setBRUSH_SIZE(currentWidth);

                }

                @Override
                protected void finalize() throws Throwable {
                    super.finalize();
                    rangeSlider.setVisibility(View.GONE);
                }
            });


            // button open the AmbilWanra color picker dialog.
            colourPicker.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // to make code look cleaner the color
                            // picker dialog functionality are
                            // handled in openColorPickerDialogue()
                            // function
                            openColorPickerDialogue();
                            rangeSlider.setVisibility(View.GONE);

                        }
                    });

            // button enables the pen
            penPicker.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            paintView.pen(currentColor);
                            rangeSlider.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Pen is selected!", Toast.LENGTH_SHORT).show();
                        }
                    });

            // button enables the eraser
            eraserPicker.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            paintView.eraser();
                            rangeSlider.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Eraser is selected!", Toast.LENGTH_SHORT).show();
                        }
                    });

            // button clears the paint board
            resetBoard.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            paintView.clear();
                            rangeSlider.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Canvas Empty!", Toast.LENGTH_SHORT).show();
                        }
                    });

            // button enables the share button
            sharePicker.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                            paintView.run(rootView);
                            rangeSlider.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Sharing Drawing", Toast.LENGTH_SHORT).show();
                        }
                    });


        }

    // the dialog functionality is handled separately
    // using openColorPickerDialog this is triggered as
    // soon as the user clicks on the Pick Color button And
    // the AmbilWarnaDialog has 2 methods to be overridden
    // those are onCancel and onOk which handle the "Cancel"
    // and "OK" button of color picker dialog
    public void openColorPickerDialogue() {

        // the AmbilWarnaDialog callback needs 3 parameters
        // one is the context, second is default color,
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, defaultcolor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        Toast.makeText(getApplicationContext(), "No colour selected", Toast.LENGTH_SHORT).show();
                        // leave this function body as
                        // blank, as the dialog
                        // automatically closes when
                        // clicked on cancel button
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // change the mDefaultColor to
                        // change the GFG text color as
                        // it is returned when the OK
                        // button is clicked from the
                        // color picker dialog
                        currentColor = color;
                        paintView.pen(currentColor);
                        String colour = ""+color;

                        // now change the picked color
                        // preview box to mDefaultColor
                        Toast.makeText(getApplicationContext(), colour+" is selected.", Toast.LENGTH_SHORT).show();
                    }
                });
        colorPickerDialogue.show();
    }

        @Override
        public boolean onCreateOptionsMenu(Menu men) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.options_menu, men);
            return super.onCreateOptionsMenu(men);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.pen:
                    //paintView.pen();
                    Toast.makeText(this, "Pen Active!", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.eraser:
                    paintView.eraser();
                    Toast.makeText(this, "Eraser Active!", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.clear:
                    paintView.clear();
                    Toast.makeText(this, "Canvas Empty!", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.share:
                    View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                    paintView.run(rootView);
                    Toast.makeText(this, "Sharing Drawing", Toast.LENGTH_SHORT).show();
                    return true;
            }


            return super.onOptionsItemSelected(item);
        }

    }