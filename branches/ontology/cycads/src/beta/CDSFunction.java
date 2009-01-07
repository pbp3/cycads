/*
 * Created on 07/01/2009
 */
package beta;

public class CDSFunction
{
	Method	method;
	String	function;

	public CDSFunction(Method method, String function) {
		this.method = method;
		this.function = function;
	}

	public Method getMethod() {
		return method;
	}

	public String getFunction() {
		return function;
	}

}
