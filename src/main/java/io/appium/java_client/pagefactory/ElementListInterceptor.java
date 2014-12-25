package io.appium.java_client.pagefactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator.FieldInteceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Intercepts requests to the list of {@link MobileElement}
 *
 */
public class ElementListInterceptor implements MethodInterceptor {
	private final ElementLocator locator;
	private FieldInteceptor interceptor;

	ElementListInterceptor(ElementLocator locator) {
		this.locator = locator;
	}

	public ElementListInterceptor(ElementLocator locator,
			FieldInteceptor interceptor) {
		super();
		this.locator = locator;
		this.interceptor = interceptor;
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		ArrayList<WebElement> realElements = new ArrayList<WebElement>();
		realElements.addAll(locator.findElements());
		if (interceptor != null) {
			if (locator instanceof AppiumElementLocator) {
				interceptor.pre(((AppiumElementLocator) locator).getBy(),
						method);
			}
		}
		return method.invoke(realElements, args);
	}

}
