package io.yodo.pragphil.view.helper;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public final class FlashHelper {

    private FlashHelper() {}

    public static void setInfo(RedirectAttributes ra, String message) {
        ra.addFlashAttribute("flashInfo", message);
    }

    public static void setError(RedirectAttributes ra, String message) {
        ra.addFlashAttribute("flashError", message);
    }
}
