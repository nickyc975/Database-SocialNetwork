package database.entities;

import java.sql.SQLException;
import java.util.Map;

import database.Model;
import database.exceptions.InvalidDataException;

/**
 * Abstract entity class.
 */
public abstract class Entity extends Model {
    /**
     * Get primary key of the entity object.
     * 
     * @return primary key.
     */
    public abstract String primaryKey();

    /**
     * Insert the newly created object into database.
     * 
     * @throws SQLException insert error.
     */
    public abstract void create() throws SQLException;

    /**
     * Load infomation of the object from database.
     * 
     * @throws SQLException load error.
     */
    public abstract void load() throws SQLException;

    /**
     * Update properties of the object with given args.
     * 
     * @param properties porpertoies.
     * @throws InvalidDataException invalid data.
     */
    public abstract void update(Map<String, String> properties) throws InvalidDataException;

    /**
     * Store properties of the object into database.
     * 
     * @throws SQLException store error.
     */
    public abstract void store() throws SQLException;

    /**
     * Delete the object from database.
     * 
     * @throws SQLException delete error.
     */
    public abstract void delete() throws SQLException;
}