package de.linzn.viki.beta.processor;

import de.linzn.viki.beta.data.GetParentSkill;
import de.linzn.viki.beta.data.GetSubSkill;
import de.linzn.viki.beta.ifaces.ISkillTemplate;
import de.linzn.viki.beta.ifaces.ParentSkill;
import de.linzn.viki.beta.ifaces.SubSkill;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SkillProcessor {

    private String rawInput = null;
    private String[] formattedInput = null;
    private ParentSkill parentSkill = null;
    private SubSkill subSkill = null;

    public SkillProcessor(String rawInput) {
        this.rawInput = rawInput;
    }

    public boolean processing() {
        this.formattingInput();
        this.buildSkill();
        return true;
    }

    private void formattingInput() {
        // Initial Symbols
        char symbols[] = {'!', '"', '§', '$', '%', '&', '/', '(', ')', '=', '?', '`', '²', '³', '{', '[', ']', '}', '*',
                '€', '@', '#', '-', '.', '_', ':', ',', ';', '<', '>', '|', 'µ', '^', '°', '~', '+', '¡', '¢', '£', '¤',
                '¥', '¦', '§', '¨', '©', 'ª', '«', '¬', '®', '¯', '°', '±', '²', '³', '´', 'µ', '¶', '·', '¸', '¹', 'º',
                '»', '¼', '½', '¾', '¿', 'À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ', 'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 'Î', 'Ï',
                'Ð', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', '×', 'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'Þ', 'ß', 'à', 'á', 'â', 'ã', 'ä',
                'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ð', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', '÷', 'ø', 'ù',
                'ú', 'û', 'ü', 'ý', 'þ', 'ÿ', '€', '‚', 'ƒ', '„', '…', '†', '‡', 'ˆ', '‰', 'Š', '‹', 'Œ', 'Ž', '‘', '’',
                '“', '”', '•', '–', '—', '˜', '™', 'š', '›', 'œ', 'ž', 'Ÿ', '¡', '¢', '£', '¤', '¥', '¦', '§', '¨', '©',
                'ª', '«', '¬', '®', '¯', '°', '±', '²', '³', '´', 'µ', '¶', '·', '¸', 'º', '»', '¼', '½', '¾', '¿', 'À',
                'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ', 'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 'Î', 'Ï', 'Ð', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ',
                'Ö', '×', 'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'Þ', 'ß', 'à', 'á', 'â', 'ã', 'ä', 'å', 'æ', 'ç', 'è', 'é', 'ê',
                'ë', 'ì', 'í', 'î', 'ï', 'ð', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', '÷', 'ø', 'ù', 'ú', 'û', 'ü', 'ý', 'þ', 'ÿ',
                '€', '‚', 'ƒ', '„', '…', '†', '‡', 'ˆ', '‰', 'Š', '‹', 'Œ', 'Ž', '‘', '’', '“', '”', '•', '–', '—', '˜',
                '™', 'š', '›', 'œ', 'ž', 'Ÿ', '¡', '¢', '£', '¤', '¥', '¦', '§', '¨', '©', 'ª', '«', '¬', '®', '¯', '°',
                '±', '²', '³', '´', 'µ', '¶', '·', '¸', 'º', '»', '¼', '½', '¾', '¿', 'À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ',
                'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 'Î', 'Ï', 'Ð', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', '×', 'Ø', 'Ù', 'Ú', 'Û',
                'Ü', 'Ý', 'Þ', 'ß', 'à', 'á', 'â', 'ã', 'ä', 'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ð',
                'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', '÷', 'ø', 'ù', 'ú', 'û', 'ü', 'ý', 'þ', 'ÿ', '€', '‚', 'ƒ', '„', '…', '†',
                '‡', 'ˆ', '‰', 'Š', '‹', 'Œ', 'Ž', '‘', '’', '“', '”', '•', '–', '—', '˜', '™', 'š', '›', 'œ', 'ž', 'Ÿ',
                '¡', '¡', '¡', '¡', '¥', '¦', '§', '¨', '©', 'ª', '«', '¬', '®', '¯', '°', '±', '²', '³', '´', 'µ', '¶',
                '·', '¸', 'º', '»', '¼', '½', '¾', '¿', 'À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ', 'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì',
                'Í', 'Î', 'Ï', 'Ð', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', '×', 'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'Þ', 'ß', 'à', 'á',
                'â', 'ã', 'ä', 'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ð', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö',
                '÷', 'ø', 'ù', 'ú', 'û', 'ü', 'ý', 'þ', 'ÿ', '^'};

        // First clean up the string
        for (char c : symbols) {
            this.rawInput = this.rawInput.replace(String.valueOf(c), "");
        }
        // For special case
        if (this.rawInput.toCharArray()[0] == ' ') {
            this.rawInput = this.rawInput.replaceFirst(" ", "");
        }

        // Replace in case than more than one spacer
        this.rawInput = this.rawInput.replaceAll("[ ]{2,}", " ");
        this.rawInput = this.rawInput.toLowerCase();

        // Split the string in substrings
        this.formattedInput = this.rawInput.split(" ");

    }


    private boolean buildSkill() {
        this.parentSkill = new GetParentSkill(this.formattedInput).getSkill();
        if (this.parentSkill != null) {
            System.out.println("Found parentSkill");
            if (!this.parentSkill.standalone) {
                this.subSkill = new GetSubSkill(this.parentSkill).getSkill();
                if (this.subSkill != null) {
                    // Code for full support with sub and parent skill
                    System.out.println("Found subSkill");
                    return this.executeJavaClassFunction();
                } else {
                    // Exit, because no subskill for this exist.
                    System.out.println("No subSkill found");
                    return false;
                }
            } else {
                System.out.println("Run parentSkill standalone");
                // Start, if parent skill ist standalone
                return this.executeJavaClassFunction();
            }
        } else {
            // If no parent skill exist!
            System.out.println("No parentSkill found");
            return false;
        }
    }

    private boolean executeJavaClassFunction() {
        String class_name;
        String method_name;
        if (subSkill == null) {
            class_name = this.parentSkill.java_class;
            method_name = this.parentSkill.java_method;
        } else {
            class_name = this.subSkill.java_class;
            method_name = this.subSkill.java_method;
        }

        if (class_name == null || method_name == null) {
            System.out.println("class_name or method_name is null");
            return false;
        }

        try {
            Class<ISkillTemplate> act = (Class<ISkillTemplate>) Class.forName("de.linzn.viki.beta.skillTemplates."
                    + Character.toUpperCase(class_name.charAt(0)) + class_name.substring(1));
            ISkillTemplate selectedSkillTemplate = act.newInstance();
            selectedSkillTemplate.setEnv(this.parentSkill, this.subSkill);

            try {
                //Search method in this class
                Method method = selectedSkillTemplate.getClass().getMethod(method_name);

                try {
                    //Run method in this class
                    method.invoke(selectedSkillTemplate);
                } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                    e.printStackTrace();
                    // Error un method run
                    return false;
                }

            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
                // Error on method search
                return false;
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
            // No class found
        }
        return true;
    }
}
