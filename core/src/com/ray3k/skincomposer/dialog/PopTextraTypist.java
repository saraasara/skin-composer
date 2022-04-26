package com.ray3k.skincomposer.dialog;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.github.tommyettinger.textra.KnownFonts;
import com.github.tommyettinger.textra.TypingLabel;
import com.ray3k.skincomposer.utils.Utils;
import com.ray3k.stripe.PopTable;

import static com.ray3k.skincomposer.Main.*;

public class PopTextraTypist extends PopTable {
    public PopTextraTypist() {
        super(new PopTableStyle());
        setFillParent(true);
        setBackground(skin.getDrawable("tt-bg"));
    
        var codeTextArea = new TextArea("", skin, "tt-page");
        var previewTypingLabel = new TypingLabel("", KnownFonts.getBitter());
//        var previewTypingLabel = new Label("", skin);
        
        var root = this;
        root.pad(10);
        root.top().left();
        
        var label = new Label("TextraTypist Playground", skin, "tt-title");
        root.add(label).left();
        
        root.row();
        var table = new Table();
        table.left();
        root.add(table).growX().spaceTop(15);
        
        var buttonGroup = new ButtonGroup<TextButton>();
        table.defaults().space(20);
        var textButton = new TextButton("File", skin, "tt-file");
        table.add(textButton);
        buttonGroup.add(textButton);
        textButton.addListener(handListener);
        
        textButton = new TextButton("Home", skin, "tt-file");
        table.add(textButton);
        buttonGroup.add(textButton);
        textButton.addListener(handListener);
    
        textButton = new TextButton("Help", skin, "tt-file");
        table.add(textButton);
        buttonGroup.add(textButton);
        textButton.addListener(handListener);
        
        root.row();
        table = new Table();
        table.left();
        table.setBackground(skin.getDrawable("tt-ribbon-10"));
        root.add(table).growX().spaceTop(10);
        
        table.defaults().space(10);
        var imageButton = new ImageButton(skin, "tt-bold");
        table.add(imageButton).padLeft(10);
        imageButton.addListener(handListener);
        Utils.onChange(imageButton, () -> insertTag("[*]", codeTextArea));
    
        imageButton = new ImageButton(skin, "tt-italics");
        table.add(imageButton);
        imageButton.addListener(handListener);
        Utils.onChange(imageButton, () -> insertTag("[/]", codeTextArea));
    
        imageButton = new ImageButton(skin, "tt-superscript");
        table.add(imageButton);
        imageButton.addListener(handListener);
        Utils.onChange(imageButton, () -> insertTag("[^]", codeTextArea));
    
        imageButton = new ImageButton(skin, "tt-subscript");
        table.add(imageButton);
        imageButton.addListener(handListener);
        Utils.onChange(imageButton, () -> insertTag("[.]", codeTextArea));
        
        imageButton = new ImageButton(skin, "tt-midscript");
        table.add(imageButton);
        imageButton.addListener(handListener);
        Utils.onChange(imageButton, () -> insertTag("[=]", codeTextArea));
    
        imageButton = new ImageButton(skin, "tt-underline");
        table.add(imageButton);
        imageButton.addListener(handListener);
        Utils.onChange(imageButton, () -> insertTag("[_]", codeTextArea));
    
        imageButton = new ImageButton(skin, "tt-strike");
        table.add(imageButton);
        imageButton.addListener(handListener);
        Utils.onChange(imageButton, () -> insertTag("[~]", codeTextArea));
    
        imageButton = new ImageButton(skin, "tt-caps");
        table.add(imageButton);
        imageButton.addListener(handListener);
        Utils.onChange(imageButton, () -> insertTag("[!]", codeTextArea));
    
        imageButton = new ImageButton(skin, "tt-lower");
        table.add(imageButton);
        imageButton.addListener(handListener);
        Utils.onChange(imageButton, () -> insertTag("[,]", codeTextArea));
    
        imageButton = new ImageButton(skin, "tt-each");
        table.add(imageButton);
        imageButton.addListener(handListener);
        Utils.onChange(imageButton, () -> insertTag("[;]", codeTextArea));
        
        var selectBox = new SelectBox<String>(skin, "tt");
        table.add(selectBox);
        selectBox.addListener(handListener);
        selectBox.getList().addListener(handListener);
    
        selectBox = new SelectBox<>(skin, "tt");
        table.add(selectBox);
        selectBox.addListener(handListener);
        selectBox.getList().addListener(handListener);
        
        selectBox = new SelectBox<>(skin, "tt");
        table.add(selectBox);
        selectBox.addListener(handListener);
        selectBox.getList().addListener(handListener);
        
        root.defaults().padLeft(20).padRight(20);
        
        previewTypingLabel.setWrap(true);
    
        root.row();
        label = new Label("CODE", skin, "tt-subtitle");
        root.add(label).left().spaceTop(15);
    
        root.row();
        codeTextArea.setName("code");
        root.add(codeTextArea).grow();
        codeTextArea.addListener(ibeamListener);
        Utils.onChange(codeTextArea, () -> {
            previewTypingLabel.setText(codeTextArea.getText());
            previewTypingLabel.restart();
        });
    
        root.row();
        imageButton = new ImageButton(skin, "tt-copy");
        root.add(imageButton).right().spaceTop(5);
        imageButton.addListener(handListener);
    
        root.row();
        label = new Label("PREVIEW", skin, "tt-subtitle");
        root.add(label).left();
        
        root.row();
        table = new Table();
        table.setBackground(skin.getDrawable("tt-page-10"));
        root.add(table).grow();
        
        var scrollPane = new ScrollPane(previewTypingLabel, skin, "tt");
        table.add(scrollPane).grow();
    
        root.row();
        table = new Table();
        root.add(table).right().spaceTop(5).padBottom(20);
        
        table.defaults().space(5);
        imageButton = new ImageButton(skin, "tt-color");
        table.add(imageButton);
        imageButton.addListener(handListener);
    
        imageButton = new ImageButton(skin, "tt-copy");
        table.add(imageButton);
        imageButton.addListener(handListener);
    }
    
    private void insertTag(String tag, TextArea textArea) {
        var originalText = textArea.getText();
        var selectionStart = Math.min(textArea.getCursorPosition(), textArea.getSelectionStart());
        var selectionEnd = Math.max(textArea.getCursorPosition(), textArea.getSelectionStart());
        var insertion = selectionStart != selectionEnd ? tag + textArea.getSelection() + tag : tag;
        var text = originalText.substring(0, selectionStart) + insertion + originalText.substring(selectionEnd);
        textArea.setText(text);
        textArea.setSelection(selectionStart, selectionStart + insertion.length());
    }
    
    @Override
    public void show(Stage stage, Action action) {
        super.show(stage, action);
        TextArea textArea = findActor("code");
        stage.setKeyboardFocus(textArea);
    }
    
    @Override
    public void hide() {
        stage.setKeyboardFocus(null);
        super.hide();
    }
}