package io.yodo.pragphil.web.view.helper;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public final class FlashHelper {

    private FlashHelper() {}

    @SuppressWarnings("unused")
    public static void setInfo(RedirectAttributes ra, String message) {
        ra.addFlashAttribute("flashInfo", message);
    }

    @SuppressWarnings("unused")
    public static void setError(RedirectAttributes ra, String message) {
        ra.addFlashAttribute("flashError", message);
    }
}
