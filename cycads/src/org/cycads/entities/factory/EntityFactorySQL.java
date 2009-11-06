/*
 * Created on 03/03/2009
 */
package org.cycads.entities.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

import org.cycads.entities.SQL.EntitySQL;
import org.cycads.entities.SQL.FeatureSQL;
import org.cycads.entities.SQL.FunctionSQL;
import org.cycads.entities.SQL.SimpleEntitySQL;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.Association;
import org.cycads.entities.annotation.SQL.AnnotationMethodSQL;
import org.cycads.entities.annotation.SQL.AnnotationSQL;
import org.cycads.entities.annotation.SQL.AssociationSQL;
import org.cycads.entities.note.Type;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.reaction.SQL.CompoundSQL;
import org.cycads.entities.reaction.SQL.ReactionSQL;
import org.cycads.entities.sequence.SQL.OrganismSQL;
import org.cycads.entities.sequence.SQL.SequenceSQL;
import org.cycads.entities.sequence.SQL.SubsequenceSQL;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.EC;
import org.cycads.entities.synonym.KO;
import org.cycads.entities.synonym.SQL.DatabaseSQL;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.ECSQL;
import org.cycads.entities.synonym.SQL.KOSQL;
import org.cycads.general.Config;
import org.cycads.general.ParametersDefault;

public class EntityFactorySQL
		implements
		EntityFactory<DbxrefSQL, AnnotationMethodSQL, TypeSQL, OrganismSQL, FunctionSQL, EntitySQL, AnnotationSQL, FeatureSQL>
{
	private Connection	con;

	public EntityFactorySQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Config.getSQLConnectionUrl(), Config.getSQLUser(),
				Config.getSQLPassword());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void finish() {
		try {
			getConnection().close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private AnnotationMethodSQL	methodDefault;

	public AnnotationMethodSQL getMethodDefault() {
		return methodDefault;
	}

	public void setMethodDefault(AnnotationMethodSQL methodDefault) {
		this.methodDefault = methodDefault;
	}

	@Override
	public AnnotationMethodSQL getAnnotationMethod(String name) {
		if (name == null || (name = name.trim()).length() == 0) {
			return getMethodDefault();
		}
		try {
			return AnnotationMethodSQL.getMethod(name, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DbxrefSQL getDbxref(String dbName, String accession) {
		try {
			return DbxrefSQL.getDbxref(dbName, accession, getConnection());
		}
		catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + " with dbName=" + dbName + " and accession=" + accession, e);
		}
	}

	public DbxrefSQL getDbxref(Dbxref dbxref) {
		if (dbxref instanceof DbxrefSQL) {
			return (DbxrefSQL) dbxref;
		}
		else {
			return getDbxref(dbxref.getDbName(), dbxref.getAccession());
		}
	}

	@Override
	public TypeSQL getType(String name) {
		return TypeSQL.getType(name, getConnection());
	}

	@Override
	public OrganismSQL getOrganism(int orgId) {
		try {
			return new OrganismSQL(orgId, getConnection());
		}
		catch (SQLException e) {
			return null;
		}
	}

	@Override
	public OrganismSQL createOrganism(int orgId, String name) {
		try {
			return OrganismSQL.createOrganism(orgId, name, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection() {
		return con;
	}

	@Override
	public KO getKO(String ko) {
		try {
			return new KOSQL(ko, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public EC getEC(String ecNumber) {
		try {
			return new ECSQL(ecNumber, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public FeatureSQL getFeature(String name) {
		try {
			return FeatureSQL.getFeature(name, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public FunctionSQL getFunction(String name) {
		try {
			return FunctionSQL.getFunction(name, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public TypeSQL getAnnotationTypeCDS() {
		return getType(ParametersDefault.getCDSAnnotationTypeName());
	}

	@Override
	public TypeSQL getAnnotationTypeGene() {
		return getType(ParametersDefault.getGeneAnnotationTypeName());
	}

	public static EntitySQL createObject(int id, int typeId, Connection con) throws SQLException {
		return createObject(id, TypeSQL.getType(typeId, con), con);
	}

	public static EntitySQL createObject(int id, TypeSQL type, Connection con) throws SQLException {
		if (type.equals(AnnotationSQL.getEntityType(con))) {
			return new AnnotationSQL<EntitySQL, EntitySQL>(id, con);
		}
		else if (type.equals(AssociationSQL.getEntityType(con))) {
			return new AssociationSQL<EntitySQL, EntitySQL>(id, con);
		}
		else if (type.equals(DbxrefSQL.getEntityType(con))) {
			return new DbxrefSQL(id, con);
		}
		else if (type.equals(FunctionSQL.getEntityType(con))) {
			return FunctionSQL.getFunction(id, con);
		}
		else if (type.equals(SubsequenceSQL.getEntityType(con))) {
			return new SubsequenceSQL(id, con);
		}
		else if (type.equals(SequenceSQL.getEntityType(con))) {
			return new SequenceSQL(id, con);
		}
		else if (type.equals(OrganismSQL.getEntityType(con))) {
			return new OrganismSQL(id, con);
		}
		else if (type.equals(ReactionSQL.getEntityType(con))) {
			return new ReactionSQL(id, con);
		}
		//		else if (type.equals(PathwaySQL.getEntityType(con))) {
		//			return new PathwaySQL(id, con);
		//		}
		else if (type.equals(CompoundSQL.getEntityType(con))) {
			return new CompoundSQL(id, con);
		}
		else if (type.equals(FeatureSQL.getEntityType(con))) {
			return FeatureSQL.getFeature(id, con);
		}
		else if (type.equals(TypeSQL.getEntityType(con))) {
			return TypeSQL.getType(id, con);
		}
		else if (type.equals(AnnotationMethodSQL.getEntityType(con))) {
			return AnnotationMethodSQL.getMethod(id, con);
		}
		else if (type.equals(DatabaseSQL.getEntityType(con))) {
			return DatabaseSQL.getDB(id, con);
		}
		return null;
	}

	@Override
	public <SO extends EntitySQL, TA extends EntitySQL> AssociationSQL<SO, TA> createAssociation(SO source, TA target,
			Collection<Type> types) {
		try {
			return AssociationSQL.createAssociationSQL(source, target, types, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <SO extends EntitySQL, TA extends EntitySQL> AnnotationSQL<SO, TA> createAnnotation(SO source, TA target,
			Collection<Type> types, AnnotationMethod method, String score) {
		try {
			return AnnotationSQL.createAnnotationSQL(source, target, types, method, score, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <SO extends EntitySQL, TA extends EntitySQL> Collection< ? extends AnnotationSQL<SO, TA>> getAnnotations(
			SO source, TA target, AnnotationMethod method, Collection<Type> types) {
		try {
			return AnnotationSQL.getAnnotations(source, target, method, types, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <TA extends EntitySQL> Collection< ? extends Annotation< ? , TA>> getAnnotations(Type sourceType, TA target,
			AnnotationMethod method, Collection<Type> types) {
		try {
			return AnnotationSQL.getAnnotations(sourceType, target, method, types, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <SO extends EntitySQL> Collection< ? extends Annotation<SO, ? >> getAnnotations(SO source, Type targetType,
			AnnotationMethod method, Collection<Type> types) {
		try {
			return AnnotationSQL.getAnnotations(source, targetType, method, types, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection< ? extends Annotation< ? , ? >> getAnnotations(Type sourceType, Type targetType,
			AnnotationMethod method, Collection<Type> types) {
		try {
			return AnnotationSQL.getAnnotations(sourceType, targetType, method, types, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <SO extends EntitySQL, TA extends EntitySQL> Collection< ? extends Association<SO, TA>> getAssociations(
			SO source, TA target, Collection<Type> types) {
		try {
			return AssociationSQL.getAssociations(source, target, types, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <TA extends EntitySQL> Collection< ? extends Association< ? , TA>> getAssociations(Type sourceType,
			TA target, Collection<Type> types) {
		try {
			return AssociationSQL.getAssociations(sourceType, target, types, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <SO extends EntitySQL> Collection< ? extends Association<SO, ? >> getAssociations(SO source,
			Type targetType, Collection<Type> types) {
		try {
			return AssociationSQL.getAssociations(source, targetType, types, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection< ? extends Association< ? , ? >> getAssociations(Type sourceType, Type targetType,
			Collection<Type> types) {
		try {
			return AssociationSQL.getAssociations(sourceType, targetType, types, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection< ? extends EntitySQL> getEntitiesBySynonym(String dbName, String accession, String type) {
		if (type == null) {
			try {
				return SimpleEntitySQL.getEntities(dbName, accession, getConnection());
			}
			catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			return SimpleEntitySQL.getEntities(TypeSQL.getType(type, getConnection()), dbName, accession,
				getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection< ? extends EntitySQL> getEntitiesBySynonym(Dbxref synonym, String type) {
		if (type == null) {
			try {
				return SimpleEntitySQL.getEntities(getDbxref(synonym), getConnection());
			}
			catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			return SimpleEntitySQL.getEntities(TypeSQL.getType(type, getConnection()), getDbxref(synonym),
				getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<AnnotationSQL< ? , ? >> getAnnotationsBySynonym(String dbName, String accession) {
		return (Collection<AnnotationSQL< ? , ? >>) getEntitiesBySynonym(dbName, accession, Annotation.ENTITY_TYPE_NAME);
	}

	@Override
	public Collection<AnnotationSQL< ? , ? >> getAnnotationsBySynonym(Dbxref synonym) {
		return (Collection<AnnotationSQL< ? , ? >>) getEntitiesBySynonym(synonym, Annotation.ENTITY_TYPE_NAME);
	}

}
