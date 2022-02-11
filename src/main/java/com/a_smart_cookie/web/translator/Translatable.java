package com.a_smart_cookie.web.translator;

import com.a_smart_cookie.entity.Language;

public interface Translatable {
	String getTranslatedValue(Language language);
}
