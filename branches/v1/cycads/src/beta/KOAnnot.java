/*
 * Created on 06/01/2009
 */
package beta;

import org.cycads.dbExternal.KO;

public class KOAnnot implements Comparable<KOAnnot>
{
	KO		ko;
	Method	method;

	public KOAnnot(KO ko, Method method) {
		this.ko = ko;
		this.method = method;
	}

	@Override
	public int compareTo(KOAnnot o) {
		return getMethod().getId() - o.getMethod().getId();
	}

	public KO getKo() {
		return ko;
	}

	public Method getMethod() {
		return method;
	}

}
