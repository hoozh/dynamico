package org.ecloga.dynamico.style;

import android.content.Context;
import android.view.View;
import android.widget.ToggleButton;
import org.json.JSONObject;

final class ToggleButtonStyler extends CompoundButtonStyler {

    ToggleButtonStyler(ViewFactory factory, Context context) {
        super(factory, context);
    }

    @Override
    public View style(View view, JSONObject attributes) throws Exception {
        super.style(view, attributes);

        ToggleButton toggleButton = (ToggleButton) view;

        if(attributes.has("textOn")) {
            toggleButton.setTextOn(attributes.getString("textOn"));
        }

        if(attributes.has("textOff")) {
            toggleButton.setTextOff(attributes.getString("textOff"));
        }

        if(attributes.has("textOn") || attributes.has("textOff")) {
            // update text immediately
            toggleButton.setChecked(toggleButton.isChecked());
        }

        return toggleButton;
    }
}