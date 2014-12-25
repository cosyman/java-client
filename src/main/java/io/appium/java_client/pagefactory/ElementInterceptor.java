package io.appium.java_client.pagefactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator.FieldInteceptor;

import java.lang.reflect.Method;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Intercepts requests to {@link MobileElement}
 *
 */
class ElementInterceptor implements MethodInterceptor {
	
	private final ElementLocator locator;
	private FieldInteceptor interceptor;

	ElementInterceptor(ElementLocator locator) {
		this.locator = locator;
	}

	public ElementInterceptor(ElementLocator locator,
			FieldInteceptor interceptor) {
		super();
		this.locator = locator;
		this.interceptor = interceptor;
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		WebElement realElement = locator.findElement();
		if (interceptor != null) {
			if (locator instanceof AppiumElementLocator) {
				interceptor.pre(((AppiumElementLocator) locator).getBy(),
						method);
			}
		}
		return method.invoke(realElement, args);
	}

}
