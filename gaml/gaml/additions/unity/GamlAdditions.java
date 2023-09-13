package gaml.additions.unity;

import msi.gama.util.matrix.*;
import msi.gama.util.graph.*;
import msi.gama.metamodel.shape.*;
import java.lang.*;
import msi.gama.util.*;
import msi.gama.util.path.*;
import msi.gama.extensions.messaging.*;
import msi.gaml.compilation.*;
import msi.gama.util.file.*;
import msi.gaml.architecture.finite_state_machine.*;
import msi.gama.common.interfaces.*;
import msi.gaml.architecture.user.*;
import msi.gama.kernel.simulation.*;
import msi.gama.outputs.*;
import msi.gaml.species.*;
import msi.gaml.architecture.weighted_tasks.*;
import msi.gama.kernel.experiment.*;
import  msi.gama.metamodel.shape.*;
import msi.gaml.extensions.multi_criteria.*;
import msi.gaml.types.*;
import msi.gama.kernel.batch.*;
import msi.gama.kernel.root.*;
import java.util.*;
import msi.gaml.factories.*;
import msi.gaml.skills.*;
import msi.gama.util.tree.*;
import msi.gama.outputs.layers.*;
import msi.gama.runtime.*;
import msi.gaml.statements.test.*;
import msi.gama.metamodel.agent.*;
import msi.gaml.expressions.*;
import msi.gama.metamodel.topology.*;
import msi.gaml.statements.draw.*;
import msi.gaml.descriptions.*;
import msi.gaml.variables.*;
import msi.gaml.operators.*;
import msi.gama.runtime.exceptions.*;
import msi.gaml.statements.*;
import msi.gama.kernel.model.*;
import msi.gama.metamodel.population.*;
import msi.gama.outputs.layers.charts.*;
import msi.gaml.architecture.reflex.*;
import msi.gaml.operators.Random;
import msi.gaml.operators.Maths;
import msi.gaml.operators.Points;
import msi.gaml.operators.Spatial.Properties;
import msi.gaml.operators.System;
import static msi.gaml.operators.Cast.*;
import static msi.gaml.operators.Spatial.*;
import static msi.gama.common.interfaces.IKeyword.*;
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })

public class GamlAdditions extends msi.gaml.compilation.AbstractGamlAdditions {
	public void initialize() throws SecurityException, NoSuchMethodException {
	initializeSpecies();
	initializeVars();
	initializeAction();
	initializeSkill();
}public void initializeSpecies()  {
_species("unity_linker",gaml.extension.unity.species.UnityLinker.class,(p, i)->new gaml.extension.unity.species.UnityLinker(p, i),S("network"));
_species("unity_player",gaml.extension.unity.species.UnityPlayer.class,(p, i)->new gaml.extension.unity.species.UnityPlayer(p, i),AS);
}public void initializeVars()  {
_var(gaml.extension.unity.skills.SentToUnitySkill.class,desc(2,S("type","2","name","heading")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.skills.SentToUnitySkill)t).getHeading(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.skills.SentToUnitySkill) t).setHeading(a, (Double) v); return null; });
_var(gaml.extension.unity.skills.SentToUnitySkill.class,desc(1,S("type","1","name","index")),(s,a,t,v)->t==null? 0:((gaml.extension.unity.skills.SentToUnitySkill)t).getIndexSpecies(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.skills.SentToUnitySkill) t).setIndexSpecues(a, (Integer) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(3,S("type","3","name","connect_to_unity","init",TRUE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.UnityLinker)t).getConnectToUnity(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setConnectToUnity(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(0,S("type","0","name","unity_client")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityLinker)t).getUnityClient(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setUnityClient(a, (Object) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(1,S("type","1","name","port","init","8000")),(s,a,t,v)->t==null? 0:((gaml.extension.unity.species.UnityLinker)t).getPort(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setPort(a, (Integer) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(4,S("type","4","name","end_message_symbol","init","&&&")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityLinker)t).getEndMessageSymbol(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setEndMessageSymbol(a, (String) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(1,S("type","1","name","precision","init","10000")),(s,a,t,v)->t==null? 0:((gaml.extension.unity.species.UnityLinker)t).getPrecision(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setPrecision(a, (Integer) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(2,S("type","2","name","delay_after_mes","init","0.0")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.UnityLinker)t).getDelayAfterMes(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setDelayAfterMes(a, (Double) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(4,S("type","4","name","waiting_message")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityLinker)t).getWaitingMessage(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setWaitingMessage(a, (String) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(5,S("type","5","name","agents_to_send","of","11")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityLinker)t).getAgentsToSend(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setAgentsToSend(a, (IList) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(5,S("type","5","name","background_geoms","of","13")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityLinker)t).getBackgroundGeoms(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setBackgroundGeoms(a, (IList) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(5,S("type","5","name","background_geoms_heights","of","1")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityLinker)t).getBackgroundGeomsHeights(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setBackgroundGeomsHeights(a, (IList) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(5,S("type","5","name","background_geoms_colliders","of","3")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityLinker)t).getBackgroundGeomsColliders(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setBackgroundGeomsColliders(a, (IList) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(5,S("type","5","name","background_geoms_names","of","4")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityLinker)t).getBackgroundGeomsNames(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setBackgroundGeomsNames(a, (IList) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(3,S("type","3","name","do_send_world","init",TRUE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.UnityLinker)t).getDoSendWorld(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setDoSendWorld(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(3,S("type","3","name","initialized","init",FALSE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.UnityLinker)t).getInitialized(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setInitialized(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(4,S("type","4","name","player_species")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityLinker)t).getPlayerSpecies(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setPlayerSpecies(a, (String) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(3,S("type","3","name","receive_information","init",TRUE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.UnityLinker)t).getReceiveInformation(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setReceiveInformation(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(3,S("type","3","name","move_player_event","init",FALSE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.UnityLinker)t).getMovePlayerEvent(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setMovePlayerEvent(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(3,S("type","3","name","create_player","init",TRUE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.UnityLinker)t).getCreatePlayer(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setCreatePlayer(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(3,S("type","3","name","move_player_from_unity","init",TRUE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.UnityLinker)t).getMovePlayerFromUnity(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setMovePlayerFromUnity(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(3,S("type","3","name","use_physics_for_player","init",TRUE)),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.UnityLinker)t).getUsePhysicsForPlayer(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setUsePhysicsForPlayer(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(5,S("type","5","name","new_player_position","of","1")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityLinker)t).getNewPlayerPosition(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setNewPlayerPosition(a, (IList) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(7,S("type","7","name","location_init","init","{50.0, 50.0}")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityLinker)t).getPlayerLocationInit(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setPlayerLocationInit(a, (GamaPoint) v); return null; });
_var(gaml.extension.unity.species.UnityLinker.class,desc(11,S("type","11","name","player_agent")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityLinker)t).getPlayer(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityLinker) t).setPlayer(a, (IAgent) v); return null; });
_var(gaml.extension.unity.species.UnityPlayer.class,desc(2,S("type","2","name","rotation")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.UnityPlayer)t).getRotation(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityPlayer) t).setRotation(a, (Double) v); return null; });
_var(gaml.extension.unity.species.UnityPlayer.class,desc(6,S("type","6","name","color")),(s,a,t,v)->t==null? null:((gaml.extension.unity.species.UnityPlayer)t).getColor(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityPlayer) t).setColor(a, (GamaColor) v); return null; });
_var(gaml.extension.unity.species.UnityPlayer.class,desc(3,S("type","3","name","to_display")),(s,a,t,v)->t==null? false:((gaml.extension.unity.species.UnityPlayer)t).getToDisplay(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityPlayer) t).setToDisplay(a, (Boolean) v); return null; });
_var(gaml.extension.unity.species.UnityPlayer.class,desc(2,S("type","2","name","cone_distance")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.UnityPlayer)t).getConeDistance(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityPlayer) t).setConeDistance(a, (Double) v); return null; });
_var(gaml.extension.unity.species.UnityPlayer.class,desc(2,S("type","2","name","cone_amplitude")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.UnityPlayer)t).getConeAmplitude(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityPlayer) t).setConeAmplitude(a, (Double) v); return null; });
_var(gaml.extension.unity.species.UnityPlayer.class,desc(2,S("type","2","name","player_size","init","3.0")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.UnityPlayer)t).getPlayerSize(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityPlayer) t).setPlayerSize(a, (Double) v); return null; });
_var(gaml.extension.unity.species.UnityPlayer.class,desc(2,S("type","2","name","player_rotation","init","90.0")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.UnityPlayer)t).getPlayerRotation(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityPlayer) t).setPlayerRotation(a, (Double) v); return null; });
_var(gaml.extension.unity.species.UnityPlayer.class,desc(2,S("type","2","name","player_agents_perception_radius","init","0.0")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.UnityPlayer)t).getPlayerAgentPerceptionRadius(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityPlayer) t).setPlayerAgentPerceptionRadius(a, (Double) v); return null; });
_var(gaml.extension.unity.species.UnityPlayer.class,desc(2,S("type","2","name","player_agents_min_dist","init","0.0")),(s,a,t,v)->t==null? 0d:((gaml.extension.unity.species.UnityPlayer)t).getPlayerAgentMinDist(a),null,(s,a,t,v)->{if (t != null) ((gaml.extension.unity.species.UnityPlayer) t).setPlayerAgentMinDist(a, (Double) v); return null; });
}public void initializeAction() throws SecurityException, NoSuchMethodException {
_action(new GamaHelper("to_map",gaml.extension.unity.skills.SentToUnitySkill.class,(s,a,t,v)->((gaml.extension.unity.skills.SentToUnitySkill) t).primToMap(s)),desc(PRIM,new Children(_arg("precision",1,T)),NAME,"to_map",TYPE,Ti(GM),VIRTUAL,FALSE),gaml.extension.unity.skills.SentToUnitySkill.class.getMethod("primToMap",SC));
_action(new GamaHelper("add_to_map",gaml.extension.unity.skills.SentToUnitySkill.class,(s,a,t,v)->{((gaml.extension.unity.skills.SentToUnitySkill) t).primAddToMap(s);return null;}),desc(PRIM,new Children(_arg("map",10,T)),NAME,"add_to_map",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.skills.SentToUnitySkill.class.getMethod("primAddToMap",SC));
_action(new GamaHelper("loc_to_send",gaml.extension.unity.skills.SentToUnitySkill.class,(s,a,t,v)->((gaml.extension.unity.skills.SentToUnitySkill) t).primLocToSend(s)),desc(PRIM,new Children(),NAME,"loc_to_send",TYPE,Ti(P),VIRTUAL,FALSE),gaml.extension.unity.skills.SentToUnitySkill.class.getMethod("primLocToSend",SC));
_action(new GamaHelper("send_world",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primSentWorld(s);return null;}),desc(PRIM,new Children(),NAME,"send_world",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primSentWorld",SC));
_action(new GamaHelper("wait_for_message",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primWaitForMessage(s);return null;}),desc(PRIM,new Children(_arg("message",4,T)),NAME,"wait_for_message",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primWaitForMessage",SC));
_action(new GamaHelper("after_sending_world",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primAfterSendingWorld(s);return null;}),desc(PRIM,new Children(),NAME,"after_sending_world",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primAfterSendingWorld",SC));
_action(new GamaHelper("add_background_data_with_names",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primAddBackgroundDataWithName(s);return null;}),desc(PRIM,new Children(_arg("geoms",5,T),_arg("names",5,T),_arg("height",2,T),_arg("collider",3,T)),NAME,"add_background_data_with_names",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primAddBackgroundDataWithName",SC));
_action(new GamaHelper("filter_overlapping",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->((gaml.extension.unity.species.UnityLinker) t).primFilterOverlapping(s)),desc(PRIM,new Children(_arg("ags",5,T)),NAME,"filter_overlapping",TYPE,Ti(LI),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primFilterOverlapping",SC));
_action(new GamaHelper("add_background_data",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primAddBackgroundData(s);return null;}),desc(PRIM,new Children(_arg("geoms",5,T),_arg("height",2,T),_arg("collider",3,T)),NAME,"add_background_data",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primAddBackgroundData",SC));
_action(new GamaHelper("send_init_data",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primSentInitData(s);return null;}),desc(PRIM,new Children(),NAME,"send_init_data",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primSentInitData",SC));
_action(new GamaHelper("new_player_location",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->((gaml.extension.unity.species.UnityLinker) t).primNewPlayerLoc(s)),desc(PRIM,new Children(_arg("loc",7,T)),NAME,"new_player_location",TYPE,Ti(P),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primNewPlayerLoc",SC));
_action(new GamaHelper("move_player",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primMovePlayer(s);return null;}),desc(PRIM,new Children(),NAME,"move_player",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primMovePlayer",SC));
_action(new GamaHelper("send_parameters",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primSendParameters(s);return null;}),desc(PRIM,new Children(),NAME,"send_parameters",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primSendParameters",SC));
_action(new GamaHelper("init_player",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primInitPlayer(s);return null;}),desc(PRIM,new Children(),NAME,"init_player",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primInitPlayer",SC));
_action(new GamaHelper("send_geometries",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primSentGeometries(s);return null;}),desc(PRIM,new Children(_arg("geoms",5,T),_arg("heights",5,T),_arg("geometry_colliders",5,T),_arg("names",5,T)),NAME,"send_geometries",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primSentGeometries",SC));
_action(new GamaHelper("after_sending_geometries",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primAfterSendingGeometries(s);return null;}),desc(PRIM,new Children(),NAME,"after_sending_geometries",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primAfterSendingGeometries",SC));
_action(new GamaHelper("filter_distance",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->((gaml.extension.unity.species.UnityLinker) t).primFilterDistance(s)),desc(PRIM,new Children(_arg("ags",5,T)),NAME,"filter_distance",TYPE,Ti(LI),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primFilterDistance",SC));
_action(new GamaHelper("manage_message_from_unity",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primManageMessageFromUnity(s);return null;}),desc(PRIM,new Children(),NAME,"manage_message_from_unity",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primManageMessageFromUnity",SC));
_action(new GamaHelper("add_JSON",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primAddJSON(s);return null;}),desc(PRIM,new Children(_arg(NAME,4,T),_arg("value",4,T)),NAME,"add_JSON",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primAddJSON",SC));
_action(new GamaHelper("message_agents",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->((gaml.extension.unity.species.UnityLinker) t).primMessageAgents(s)),desc(PRIM,new Children(_arg("ags",5,T)),NAME,"message_agents",TYPE,Ti(LI),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primMessageAgents",SC));
_action(new GamaHelper("send_player_position",gaml.extension.unity.species.UnityLinker.class,(s,a,t,v)->{((gaml.extension.unity.species.UnityLinker) t).primSendPlayerPosition(s);return null;}),desc(PRIM,new Children(),NAME,"send_player_position",TYPE,Ti(void.class),VIRTUAL,FALSE),gaml.extension.unity.species.UnityLinker.class.getMethod("primSendPlayerPosition",SC));
_action(new GamaHelper("player_perception_cone",gaml.extension.unity.species.UnityPlayer.class,(s,a,t,v)->((gaml.extension.unity.species.UnityPlayer) t).primGetCone(s)),desc(PRIM,new Children(),NAME,"player_perception_cone",TYPE,Ti(IS),VIRTUAL,FALSE),gaml.extension.unity.species.UnityPlayer.class.getMethod("primGetCone",SC));
}public void initializeSkill()  {
_skill("sent_to_unity",gaml.extension.unity.skills.SentToUnitySkill.class,AS);
}
}