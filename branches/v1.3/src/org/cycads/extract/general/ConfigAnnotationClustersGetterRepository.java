/*
 * Created on 24/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

import org.cycads.extract.score.AnnotationWayListScoreSystem;
import org.cycads.general.Config;

public class ConfigAnnotationClustersGetterRepository implements AnnotationClustersGetterRepository
{

	@Override
	public AnnotationClustersGetter getAnnotationClusterGetter(String name) {
		List<String> locations = Config.getAnnotationClusterLocs(name);
		AnnotationWayListScoreSystem scoreSystem = Config.getAnnotationClusterScoreSystems(name);

		return new SimpleAnnotationClustersGetter(locations, scoreSystem);
	}

}
