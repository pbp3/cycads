/*
 * Created on 06/01/2009
 */
package beta;

import org.cycads.dbExternal.KO;

public class KOAnnot
{
	KO		ko;
	Method	method;

	protected KOAnnot(KO ko, Method method) {
		this.ko = ko;
		this.method = method;
	}

}
