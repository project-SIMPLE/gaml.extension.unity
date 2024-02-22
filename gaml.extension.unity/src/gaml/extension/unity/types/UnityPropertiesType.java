/*******************************************************************************************************
 *
 * UnityPropertiesType.java, in gaml.extension.unity, is part of the source code of the GAMA modeling and simulation
 * platform (v.1.9.3).
 *
 * (c) 2007-2024 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.extension.unity.types;

import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.type;
import gama.annotations.precompiler.IConcept;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.gaml.types.GamaType;
import gama.gaml.types.IType;

/**
 * The Class UnityPropertiesType.
 */
@type (
		name = "unity_property",
		id = UnityPropertiesType.UNITYPROPERTIESTYPE_ID,
		wraps = { UnityProperties.class },
		concept = { IConcept.TYPE, "Unity" })
@doc ("a type representing a set of properties for the geometry/agent to send to Unity")
public class UnityPropertiesType extends GamaType<UnityProperties> {

	/** The Constant id. */
	public final static int UNITYPROPERTIESTYPE_ID = IType.AVAILABLE_TYPES + 28463525;

	/**
	 * Can cast to const.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean canCastToConst() {
		return true;
	}

	/**
	 * Cast.
	 *
	 * @param scope
	 *            the scope
	 * @param obj
	 *            the obj
	 * @param val
	 *            the val
	 * @param copy
	 *            the copy
	 * @return the unity properties
	 * @throws GamaRuntimeException
	 *             the gama runtime exception
	 */
	@Override
	@doc ("cast an object into a unity_properties if it is an instance of a unity_properties")
	public UnityProperties cast(final IScope scope, final Object obj, final Object val, final boolean copy)
			throws GamaRuntimeException {
		if (obj instanceof UnityProperties) return (UnityProperties) obj;
		return null;
	}

	/**
	 * Gets the default.
	 *
	 * @return the default
	 */
	@Override
	public UnityProperties getDefault() { return null; }

}
