package gaml.additions.unity;

import gama.gaml.multi_criteria.*;
import gama.core.runtime.exceptions.*;
import java.lang.*;
import gama.gaml.descriptions.*;
import gama.core.outputs.layers.*;
import gama.gaml.architecture.user.*;
import gama.gaml.architecture.reflex.*;
import gama.gaml.statements.test.*;
import gama.core.util.*;
import gama.gaml.architecture.finite_state_machine.*;
import gama.gaml.types.*;
import  gama.core.metamodel.shape.*;
import gama.core.metamodel.shape.*;
import gama.core.outputs.layers.charts.*;
import gama.gaml.operators.*;
import gama.core.metamodel.population.*;
import gama.core.util.tree.*;
import gama.core.util.matrix.*;
import gama.gaml.compilation.*;
import gama.core.kernel.root.*;
import gama.gaml.factories.*;
import gama.gaml.skills.*;
import gama.core.util.path.*;
import gama.core.kernel.experiment.*;
import java.util.*;
import gama.gaml.statements.draw.*;
import gama.core.util.graph.*;
import gama.gaml.statements.*;
import gama.gaml.architecture.weighted_tasks.*;
import gama.core.kernel.model.*;
import gama.core.outputs.*;
import gama.core.metamodel.topology.*;
import gama.core.metamodel.agent.*;
import gama.gaml.expressions.*;
import gama.core.util.file.*;
import gama.core.kernel.batch.*;
import gama.gaml.species.*;
import gama.gaml.variables.*;
import gama.core.common.interfaces.*;
import gama.core.runtime.*;
import gama.core.messaging.*;
import gama.core.kernel.simulation.*;
import gama.gaml.operators.Random;
import gama.gaml.operators.Maths;
import gama.gaml.operators.Points;
import gama.gaml.operators.Spatial.Properties;
import gama.gaml.operators.System;
import static gama.gaml.operators.Cast.*;
import static gama.gaml.operators.Spatial.*;
import static gama.core.common.interfaces.IKeyword.*;
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })

public class GamlAdditions extends gama.gaml.compilation.AbstractGamlAdditions {
	public void initialize() throws SecurityException, NoSuchMethodException {
	initializeType();
	initializeSpecies();
	initializeVars();
	initializeOperator();
	initializeAction();
	initializeExperiment();
}public void initializeType()  {
_type("unity_aspect",new gaml.extension.unity.types.UnityAspectType(),352633,104,gaml.extension.unity.types.UnityAspect.class);
_type("unity_interaction",new gaml.extension.unity.types.UnityInteractionType(),383786,104,gaml.extension.unity.types.UnityInteraction.class);
_type("unity_property",new gaml.extension.unity.types.UnityPropertiesType(),28463575,104,gaml.extension.unity.types.UnityProperties.class);
}public void initializeSpecies()  {
_species("abstract_unity_linker",gaml.extension.unity.species.AbstractUnityLinker.class,(p, i)->new gaml.extension.unity.species.AbstractUnityLinker(p, i),S("network"));
_species("abstract_unity_player",gaml.extension.unity.species.AbstractUnityPlayer.class,(p, i)->new gaml.extension.unity.species.AbstractUnityPlayer(p, i),AS);
}public void initializeVars()  {
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(3,S("type","3","name","connect_to_unity","init",TRUE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.AbstractUnityLinker)t).getConnectToUnity(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setConnectToUnity(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(5,S("type","5","name","ready_to_move_player","init","[]")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.AbstractUnityLinker)t).getReadyToMovePlayers(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setReadyToMovePlayers(a, (IList) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(1,S("type","1","name","min_num_players","init","0")),(s,a,t,v)->t==null? 0:((gaml.extension.unity.species.AbstractUnityLinker)t).getMinPlayer(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setMinPlayer(a, (Integer) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(1,S("type","1","name","max_num_players","init","1")),(s,a,t,v)->t==null? 0:((gaml.extension.unity.species.AbstractUnityLinker)t).getMaxPlayer(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setMaxPlayer(a, (Integer) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(1,S("type","1","name","precision","init","10000")),(s,a,t,v)->t==null? 0:((gaml.extension.unity.species.AbstractUnityLinker)t).getPrecision(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setPrecision(a, (Integer) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(5,S("type","5","name","unity_properties","of","28463575")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.AbstractUnityLinker)t).getUnityProperties(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setUnityProperties(a, (IList) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(10,S("type","10","name","background_geometries")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.AbstractUnityLinker)t).getBackgroundGeometries(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setBackgroundGeometries(a, (IMap) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(10,S("type","10","name","geometries_to_send")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.AbstractUnityLinker)t).getGeometriesToSend(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setGeometriesToSend(a, (IMap) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(3,S("type","3","name","do_send_world","init",TRUE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.AbstractUnityLinker)t).getDoSendWorld(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setDoSendWorld(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(3,S("type","3","name","initialized","init",FALSE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.AbstractUnityLinker)t).getInitialized(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setInitialized(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(4,S("type","4","name","player_species")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.AbstractUnityLinker)t).getPlayerSpecies(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setPlayerSpecies(a, (String) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(4,S("type","4","name","end_message_symbol")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.AbstractUnityLinker)t).getEndMessageSymbol(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setEndMessageSymbol(a, (String) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(3,S("type","3","name","receive_information","init",FALSE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.AbstractUnityLinker)t).getReceiveInformation(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setReceiveInformation(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(3,S("type","3","name","move_player_event","init",FALSE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.AbstractUnityLinker)t).getMovePlayerEvent(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setMovePlayerEvent(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(3,S("type","3","name","move_player_from_unity","init",TRUE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.AbstractUnityLinker)t).getMovePlayerFromUnity(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setMovePlayerFromUnity(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(3,S("type","3","name","use_middleware","init",TRUE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.AbstractUnityLinker)t).getUseMiddleware(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setUseMiddleware(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(10,S("type","10","name","new_player_position")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.AbstractUnityLinker)t).getNewPlayerPosition(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setNewPlayerPosition(a, (IMap) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(2,S("type","2","name","distance_player_selection","init","2.0")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.AbstractUnityLinker)t).getDistanceSelection(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setDistanceSelection(a, (Double) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(5,S("type","5","name","init_locations","of","7")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.AbstractUnityLinker)t).getPlayerLocationInit(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setPlayerLocationInit(a, (IList) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityLinker.class,desc(10,S("type","10","name","player_agents")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.AbstractUnityLinker)t).getPlayers(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityLinker) t).setPlayers(a, (IMap) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityPlayer.class,desc(2,S("type","2","name","rotation")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.AbstractUnityPlayer)t).getRotation(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityPlayer) t).setRotation(a, (Double) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityPlayer.class,desc(6,S("type","6","name","color")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.AbstractUnityPlayer)t).getColor(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityPlayer) t).setColor(a, (GamaColor) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityPlayer.class,desc(3,S("type","3","name","to_display")),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.AbstractUnityPlayer)t).getToDisplay(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityPlayer) t).setToDisplay(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityPlayer.class,desc(3,S("type","3","name","selected")),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.AbstractUnityPlayer)t).getSelected(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityPlayer) t).setSelected(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityPlayer.class,desc(2,S("type","2","name","cone_distance")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.AbstractUnityPlayer)t).getConeDistance(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityPlayer) t).setConeDistance(a, (Double) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityPlayer.class,desc(2,S("type","2","name","cone_amplitude")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.AbstractUnityPlayer)t).getConeAmplitude(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityPlayer) t).setConeAmplitude(a, (Double) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityPlayer.class,desc(2,S("type","2","name","player_size","init","3.0")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.AbstractUnityPlayer)t).getPlayerSize(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityPlayer) t).setPlayerSize(a, (Double) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityPlayer.class,desc(2,S("type","2","name","player_rotation","init","90.0")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.AbstractUnityPlayer)t).getPlayerRotation(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityPlayer) t).setPlayerRotation(a, (Double) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityPlayer.class,desc(2,S("type","2","name","player_agents_perception_radius","init","0.0")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.AbstractUnityPlayer)t).getPlayerAgentPerceptionRadius(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityPlayer) t).setPlayerAgentPerceptionRadius(a, (Double) v); return null; });
_var(gaml.extension.unity.species.AbstractUnityPlayer.class,desc(2,S("type","2","name","player_agents_min_dist","init","0.0")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.AbstractUnityPlayer)t).getPlayerAgentMinDist(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.AbstractUnityPlayer) t).setPlayerAgentMinDist(a, (Double) v); return null; });
_var(gaml.extension.unity.species.VRExperiment.class,desc(4,S("type","4","name","unity_linker_species")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.VRExperiment)t).getUnityLinkerSpecies(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.VRExperiment) t).setUnityLinkerSpecies(a, (String) v); return null; });
_var(gaml.extension.unity.species.VRExperiment.class,desc(11,S("type","11","name","unity_linker")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.VRExperiment)t).getUnityLinker(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.VRExperiment) t).setUnityLinker(a, (IAgent) v); return null; });
_var(gaml.extension.unity.species.VRExperiment.class,desc(5,S("type","5","name","displays_to_hide")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.VRExperiment)t).getDisplaysToHide(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.VRExperiment) t).setDisplaysToHide(a, (IList) v); return null; });
_field(gaml.extension.unity.types.UnityAspect.class,"prefab",(s, o)->((gaml.extension.unity.types.UnityAspect)o[0]).getPrefab(),4,gaml.extension.unity.types.UnityAspect.class,4,0,0);
_field(gaml.extension.unity.types.UnityAspect.class,"size",(s, o)->((gaml.extension.unity.types.UnityAspect)o[0]).getSize(),2,gaml.extension.unity.types.UnityAspect.class,2,0,0);
_field(gaml.extension.unity.types.UnityAspect.class,"rotation_coeff",(s, o)->((gaml.extension.unity.types.UnityAspect)o[0]).getRotation_coeff(),2,gaml.extension.unity.types.UnityAspect.class,2,0,0);
_field(gaml.extension.unity.types.UnityAspect.class,"rotation_offset",(s, o)->((gaml.extension.unity.types.UnityAspect)o[0]).getRotation_offset(),2,gaml.extension.unity.types.UnityAspect.class,2,0,0);
_field(gaml.extension.unity.types.UnityAspect.class,"y_offset",(s, o)->((gaml.extension.unity.types.UnityAspect)o[0]).getY_offset(),2,gaml.extension.unity.types.UnityAspect.class,2,0,0);
_field(gaml.extension.unity.types.UnityAspect.class,"height",(s, o)->((gaml.extension.unity.types.UnityAspect)o[0]).getHeight(),2,gaml.extension.unity.types.UnityAspect.class,2,0,0);
_field(gaml.extension.unity.types.UnityAspect.class,"color",(s, o)->((gaml.extension.unity.types.UnityAspect)o[0]).getColor(),6,gaml.extension.unity.types.UnityAspect.class,6,0,0);
_field(gaml.extension.unity.types.UnityInteraction.class,"has_collider",null,3,gaml.extension.unity.types.UnityInteraction.class,3,0,0);
_field(gaml.extension.unity.types.UnityInteraction.class,"is_trigger",null,3,gaml.extension.unity.types.UnityInteraction.class,3,0,0);
_field(gaml.extension.unity.types.UnityInteraction.class,"is_interactable",null,3,gaml.extension.unity.types.UnityInteraction.class,3,0,0);
_field(gaml.extension.unity.types.UnityInteraction.class,"is_grabable",null,3,gaml.extension.unity.types.UnityInteraction.class,3,0,0);
_field(gaml.extension.unity.types.UnityProperties.class,"id",null,4,gaml.extension.unity.types.UnityProperties.class,4,0,0);
_field(gaml.extension.unity.types.UnityProperties.class,"aspect",null,352633,gaml.extension.unity.types.UnityProperties.class,352633,0,0);
_field(gaml.extension.unity.types.UnityProperties.class,"interaction",null,383786,gaml.extension.unity.types.UnityProperties.class,383786,0,0);
_field(gaml.extension.unity.types.UnityProperties.class,"tag",null,4,gaml.extension.unity.types.UnityProperties.class,4,0,0);
}public void initializeOperator() throws SecurityException, NoSuchMethodException {
_operator(S("geometry_ray"),gaml.extension.unity.operators.Operators.class.getMethod("newUnityGeometryRay",b),null,AI,gaml.extension.unity.types.UnityInteraction.class,T,-13,-13,-13,-13,(s,o)->gaml.extension.unity.operators.Operators.newUnityGeometryRay(asBool(s,o[0])),F);
_operator(S("new_geometry_interaction"),gaml.extension.unity.operators.Operators.class.getMethod("newUnityGeometryInteraction",b,b,b,b),null,AI,gaml.extension.unity.types.UnityInteraction.class,T,-13,-13,-13,-13,(s,o)->gaml.extension.unity.operators.Operators.newUnityGeometryInteraction(asBool(s,o[0]),asBool(s,o[1]),asBool(s,o[2]),asBool(s,o[3])),F);
_operator(S("geometry_aspect"),gaml.extension.unity.operators.Operators.class.getMethod("newUnityGeometryAspect",d,GC,i),null,AI,gaml.extension.unity.types.UnityAspect.class,T,-13,-13,-13,-13,(s,o)->gaml.extension.unity.operators.Operators.newUnityGeometryAspect(asFloat(s,o[0]),((GamaColor)o[1]),asInt(s,o[2])),F);
_operator(S("geometry_grabable"),gaml.extension.unity.operators.Operators.class.getMethod("newUnityGeometryGrabable",b),null,AI,gaml.extension.unity.types.UnityInteraction.class,T,-13,-13,-13,-13,(s,o)->gaml.extension.unity.operators.Operators.newUnityGeometryGrabable(asBool(s,o[0])),F);
_operator(S("geometry_properties"),gaml.extension.unity.operators.Operators.class.getMethod("newUnityGeometrytoSend",S,S,gaml.extension.unity.types.UnityAspect.class,gaml.extension.unity.types.UnityInteraction.class),null,AI,gaml.extension.unity.types.UnityProperties.class,T,-13,-13,-13,-13,(s,o)->gaml.extension.unity.operators.Operators.newUnityGeometrytoSend(((String)o[0]),((String)o[1]),((gaml.extension.unity.types.UnityAspect)o[2]),((gaml.extension.unity.types.UnityInteraction)o[3])),F);
_operator(S("prefab_aspect"),gaml.extension.unity.operators.Operators.class.getMethod("newUnityPrefabAspect",S,d,d,d,d,i),null,AI,gaml.extension.unity.types.UnityAspect.class,T,-13,-13,-13,-13,(s,o)->gaml.extension.unity.operators.Operators.newUnityPrefabAspect(((String)o[0]),asFloat(s,o[1]),asFloat(s,o[2]),asFloat(s,o[3]),asFloat(s,o[4]),asInt(s,o[5])),F);
_operator(S("geometry_properties_no_interaction"),gaml.extension.unity.operators.Operators.class.getMethod("newUnityGeometrytoSendNoInt",S,S,gaml.extension.unity.types.UnityAspect.class),null,AI,gaml.extension.unity.types.UnityProperties.class,T,-13,-13,-13,-13,(s,o)->gaml.extension.unity.operators.Operators.newUnityGeometrytoSendNoInt(((String)o[0]),((String)o[1]),((gaml.extension.unity.types.UnityAspect)o[2])),F);
}public void initializeAction() throws SecurityException, NoSuchMethodException {
_action(new GamaHelper("send_player_position",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primSendPlayerPosition(s);return null;}),desc(PRIM,new Children(_arg("player",11,T)),NAME,"send_player_position",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primSendPlayerPosition",SC));
_action(new GamaHelper("send_init_data",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primSentInitData(s);return null;}),desc(PRIM,new Children(_arg("id",4,T)),NAME,"send_init_data",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primSentInitData",SC));
_action(new GamaHelper("send_geometries",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primSentGeometries(s);return null;}),desc(PRIM,new Children(_arg("player",11,T),_arg("update_position",3,T),_arg("geoms",10,T)),NAME,"send_geometries",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primSentGeometries",SC));
_action(new GamaHelper("create_init_player",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primCreateInitPlayer(s);return null;}),desc(PRIM,new Children(_arg("id",4,T)),NAME,"create_init_player",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primCreateInitPlayer",SC));
_action(new GamaHelper("send_unity_propetries",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primSendUnityProperties(s);return null;}),desc(PRIM,new Children(_arg("player",11,T)),NAME,"send_unity_propetries",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primSendUnityProperties",SC));
_action(new GamaHelper("message_geometry_loc",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->((gaml.extension.unity.species.AbstractUnityLinker) t).primMessageGeoms(s)),desc(PRIM,new Children(_arg("geom",13,T)),NAME,"message_geometry_loc",TYPE,Ti(GM),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primMessageGeoms",SC));
_action(new GamaHelper("move_player",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primMovePlayer(s);return null;}),desc(PRIM,new Children(_arg("player",11,T),_arg("loc",7,T)),NAME,"move_player",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primMovePlayer",SC));
_action(new GamaHelper("send_parameters",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primSendParameters(s);return null;}),desc(PRIM,new Children(_arg("player",11,T)),NAME,"send_parameters",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primSendParameters",SC));
_action(new GamaHelper("message_geometry_shape",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->((gaml.extension.unity.species.AbstractUnityLinker) t).primMessageGeomsShape(s)),desc(PRIM,new Children(_arg("geom",13,T)),NAME,"message_geometry_shape",TYPE,Ti(GM),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primMessageGeomsShape",SC));
_action(new GamaHelper("player_position_updated",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primPlayerPositionUpdated(s);return null;}),desc(PRIM,new Children(_arg("id",4,T)),NAME,"player_position_updated",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primPlayerPositionUpdated",SC));
_action(new GamaHelper("add_to_send_parameter",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primAddToSentParameter(s);return null;}),desc(PRIM,new Children(_arg("map_to_send",10,T)),NAME,"add_to_send_parameter",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primAddToSentParameter",SC));
_action(new GamaHelper("filter_distance",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->((gaml.extension.unity.species.AbstractUnityLinker) t).primFilterDistance(s)),desc(PRIM,new Children(_arg("geometries",5,T),_arg("player",11,T)),NAME,"filter_distance",TYPE,Ti(LI),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primFilterDistance",SC));
_action(new GamaHelper("add_to_map",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primAddToMap(s);return null;}),desc(PRIM,new Children(_arg("map",10,T),_arg("geom",13,T)),NAME,"add_to_map",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primAddToMap",SC));
_action(new GamaHelper("new_player_location",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->((gaml.extension.unity.species.AbstractUnityLinker) t).primNewPlayerLoc(s)),desc(PRIM,new Children(_arg("loc",7,T),_arg("player",11,T)),NAME,"new_player_location",TYPE,Ti(P),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primNewPlayerLoc",SC));
_action(new GamaHelper("send_world",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primSentWorld(s);return null;}),desc(PRIM,new Children(),NAME,"send_world",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primSentWorld",SC));
_action(new GamaHelper("move_player_external",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primMovePlayerFromUnity(s);return null;}),desc(PRIM,new Children(_arg("id",4,T),_arg("x",1,T),_arg("y",1,T),_arg("z",1,T),_arg("angle",1,T)),NAME,"move_player_external",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primMovePlayerFromUnity",SC));
_action(new GamaHelper("send_message",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primSentMessage(s);return null;}),desc(PRIM,new Children(_arg("players",5,T),_arg("mes",10,T)),NAME,"send_message",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primSentMessage",SC));
_action(new GamaHelper("loc_to_send",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->((gaml.extension.unity.species.AbstractUnityLinker) t).primLocToSend(s)),desc(PRIM,new Children(),NAME,"loc_to_send",TYPE,Ti(P),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primLocToSend",SC));
_action(new GamaHelper("after_sending_geometries",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primAfterSendingGeometries(s);return null;}),desc(PRIM,new Children(_arg("player",11,T)),NAME,"after_sending_geometries",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primAfterSendingGeometries",SC));
_action(new GamaHelper("after_sending_world",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primAfterSendingWorld(s);return null;}),desc(PRIM,new Children(_arg("map_to_send",10,T)),NAME,"after_sending_world",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primAfterSendingWorld",SC));
_action(new GamaHelper("create_player",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primInitPlayer(s);return null;}),desc(PRIM,new Children(_arg("id",4,T)),NAME,"create_player",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primInitPlayer",SC));
_action(new GamaHelper("filter_overlapping",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->((gaml.extension.unity.species.AbstractUnityLinker) t).primFilterOverlapping(s)),desc(PRIM,new Children(_arg("geometries",5,T),_arg("player",11,T)),NAME,"filter_overlapping",TYPE,Ti(LI),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primFilterOverlapping",SC));
_action(new GamaHelper("ping_GAMA",gaml.extension.unity.species.AbstractUnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.AbstractUnityLinker) t).primPingGAMA(s);return null;}),desc(PRIM,new Children(_arg("id",4,T)),NAME,"ping_GAMA",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityLinker.class.getMethod("primPingGAMA",SC));
_action(new GamaHelper("player_perception_cone",gaml.extension.unity.species.AbstractUnityPlayer.class,(s,a,t,v)->((gaml.extension.unity.species.AbstractUnityPlayer) t).primGetCone(s)),desc(PRIM,new Children(),NAME,"player_perception_cone",TYPE,Ti(IS),VIRTUAL,FALSE),gaml.extension.unity.species.AbstractUnityPlayer.class.getMethod("primGetCone",SC));
}public void initializeExperiment()  {
_experiment("unity",(p, i)->new gaml.extension.unity.species.VRExperiment(p, i),gaml.extension.unity.species.VRExperiment.class);
}
}