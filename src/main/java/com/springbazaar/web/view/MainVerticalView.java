package com.springbazaar.web.view;

import com.vaadin.navigator.View;
import com.vaadin.server.Sizeable;
import com.vaadin.server.UserError;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MainVerticalView extends VerticalLayout implements View {
    static int tabIndex = 0;
    private final List<TextField> mandatoryFields = new ArrayList<>();
    boolean needFillFlag = false;

    protected void settingAndLayoutField(VerticalLayout layout, TextField field, String caption, boolean isRequired) {
        field.setWidth(25.0f, Sizeable.Unit.PERCENTAGE);
        field.setTabIndex(tabIndex);
        tabIndex++;
        field.setCaption(caption);
        field.setMaxLength(25);
        field.setRequiredIndicatorVisible(isRequired);
        if (isRequired) {
            mandatoryFields.add(field);
        }
        layout.addComponent(field);
    }

    protected void checkFilledMandatoryFields() {
        for (TextField field : mandatoryFields) {
            if (StringUtils.isEmpty(field.getValue())) {
                updateCaption(field);
                needFillFlag = true;
            }
        }
    }

    protected void updateCaption(TextField textField) {
        textField.setPlaceholder("This mandatory field");
        textField.setComponentError(new UserError("Please fill this field"));
        Notification.show("Please fill mandatory fields with *", Notification.Type.TRAY_NOTIFICATION);
    }
}
