package dev.pk7r.interactive.jda.annotation.modal;

import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MetaItemComponent {

    // Not required if @MetaItemComponent references a TextInput
    String id() default "";

    ItemComponentType type() default ItemComponentType.TEXT_INPUT;

    MetaTextInput textInput() default @MetaTextInput(style = TextInputStyle.SHORT, id = "text", label = "Input Text");

}