package com.springbazaar.web.ui.tool.component;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

public class SafeDeleteButton extends CustomComponent {

    private final Label infoLabel = new Label("", ContentMode.HTML);
    private final Button confirmButton = new Button("Confirm", VaadinIcons.CHECK);
    private final Button cancelButton = new Button("Cancel", VaadinIcons.CLOSE);
    private final Window window = new Window();

    public SafeDeleteButton(String caption,
                            String popupText,
                            Button.ClickListener yesListener) {
        VerticalLayout root = new VerticalLayout();
        setCompositionRoot(root);
        root.setSpacing(false);
        root.setMargin(false);
        infoLabel.setSizeFull();
        if (popupText != null) {
            infoLabel.setValue(popupText);
        }

        final VerticalLayout popupVLayout = new VerticalLayout();
        popupVLayout.setSpacing(true);
        popupVLayout.setMargin(true);
        final Button button = new Button(caption, VaadinIcons.TRASH);
        button.setSizeFull();
        button.addClickListener(e -> {
            if (window.getParent() == null) {
                UI.getCurrent().addWindow(window);
            }
        });
        HorizontalLayout buttonsHLayout = new HorizontalLayout();
        buttonsHLayout.setSpacing(true);
        buttonsHLayout.addComponent(confirmButton);
        buttonsHLayout.addComponent(cancelButton);
        window.setWidth("350px");
        window.setHeightUndefined();
        window.setModal(true);
        window.center();
        window.setResizable(false);
        window.setContent(popupVLayout);
        window.setCaption(" Confirm");
        window.setIcon(VaadinIcons.QUESTION_CIRCLE_O);
        confirmButton.addClickListener(e -> {
            window.close();
        });
        if (yesListener != null) {
            confirmButton.addClickListener(yesListener);
        }
        cancelButton.focus();
        cancelButton.addClickListener(e -> {
            window.close();
        });

        popupVLayout.addComponent(infoLabel);
        popupVLayout.addComponent(buttonsHLayout);
        popupVLayout.setComponentAlignment(buttonsHLayout, Alignment.TOP_CENTER);
        root.addComponent(button);
    }

    public void setInfo(String info) {
        infoLabel.setValue(info);
    }

    public void setWindowWidth(String width) {
        window.setWidth(width);
    }

    public void setYesListener(Button.ClickListener yesListener) {
        confirmButton.addClickListener(yesListener);
    }
}
