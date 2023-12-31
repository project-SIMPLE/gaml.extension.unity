package gaml.extension.unity.species;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.extensions.messaging.GamaMessage;
import msi.gama.kernel.root.PlatformAgent;
import msi.gama.metamodel.agent.GamlAgent;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.metamodel.population.IPopulation;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.metamodel.shape.IShape;
import msi.gama.precompiler.GamlAnnotations.action;
import msi.gama.precompiler.GamlAnnotations.arg;
import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.getter;
import msi.gama.precompiler.GamlAnnotations.setter;
import msi.gama.precompiler.GamlAnnotations.species;
import msi.gama.precompiler.GamlAnnotations.variable;
import msi.gama.precompiler.GamlAnnotations.vars;
import msi.gama.runtime.GAMA;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.util.GamaListFactory;
import msi.gama.util.GamaMap;
import msi.gama.util.GamaMapFactory;
import msi.gama.util.IList;
import msi.gama.util.IMap;
import msi.gama.util.file.json.JsonObject;
import msi.gaml.descriptions.ConstantExpressionDescription;
import msi.gaml.operators.Cast;
import msi.gaml.operators.Spatial;
import msi.gaml.operators.Spatial.Creation;
import msi.gaml.operators.Spatial.Punctal;
import msi.gaml.operators.Spatial.Queries;
import msi.gaml.operators.Spatial.Transformations;
import msi.gaml.species.ISpecies;
import msi.gaml.statements.Arguments;
import msi.gaml.statements.IStatement.WithArgs;
import msi.gaml.types.IType;
import msi.gaml.types.Types;
import ummisco.gama.network.skills.INetworkSkill;
import ummisco.gama.serializer.gaml.SerialisationOperators;

@species(name = "abstract_unity_linker", skills={"network"})
@vars({ 
	@variable(name = AbstractUnityLinker.CONNECT_TO_UNITY, type = IType.BOOL, init = "true",
			doc = { @doc ("Activate the unity connection; if activated, the model will wait for an connection from Unity to start")}), 
	@variable(name = AbstractUnityLinker.CLIENT, type = IType.NONE,
	doc = { @doc ("Client (middleware) to which the messages are sent")}), 

	@variable(name = AbstractUnityLinker.MIN_NUMBER_PLAYERS, type = IType.INT, init = "0",
	doc = { @doc ("Number of Unity players required to start the simulation")}), 
	@variable(name = AbstractUnityLinker.MAX_NUMBER_PLAYERS, type = IType.INT, init = "1",
	doc = { @doc ("Maximal number of Unity players")}), 

	@variable(name =AbstractUnityLinker.PORT, type = IType.INT, init="6870",
			doc = { @doc ("Connection port")}), 
	//@variable(name = AbstractUnityLinker.END_MESSAGE_SYMBOL, type = IType.STRING, init = "'&&&'", 
	//		doc = { @doc ("Symbol used to end a message sent to Unity")}), 
	@variable(name = AbstractUnityLinker.PRECISION, type = IType.INT, init = "10000", 
			doc = { @doc ("Number of decimal for the data (location, rotation)")}),  
	//@variable(name = AbstractUnityLinker.DELAY_AFTER_MES, type = IType.FLOAT, init = "0.0", 
//			doc = { @doc ("Delay after moving the player (in ms)")}), 
//	@variable(name = AbstractUnityLinker.WAITING_MESSAGE, type = IType.STRING, 
//	doc = { @doc ("Which message GAMA should wait before receiving infomation ")}), 

	@variable(name = AbstractUnityLinker.AGENTS_TO_SEND, type = IType.LIST, of =  IType.AGENT, 
			doc = { @doc ("List of agents to sent to Unity. It could be updated each simulation step")}),  
	
	@variable(name = AbstractUnityLinker.BACKGROUND_GEOMS, type = IType.LIST, of =  IType.GEOMETRY, 
			doc = { @doc ("List of static geometries sent to Unity. Only sent once at the initialization of the connection")}), 

	@variable(name = AbstractUnityLinker.BACKGROUND_GEOMS_HEIGHTS, type = IType.LIST, of =  IType.INT, 
			doc = { @doc ("For each geometry sent to Unity, the height of this one.")}),  

	@variable(name = AbstractUnityLinker.BACKGROUND_GEOMS_COLLIDERS, type = IType.LIST, of =  IType.BOOL, 
			doc = { @doc ("For each geometry sent to Unity, does this one has a collider (i.e. a physical existence) ? ")}),  
	@variable(name = AbstractUnityLinker.BACKGROUND_GEOMS_3D, type = IType.LIST, of =  IType.BOOL, 
	doc = { @doc ("For each geometry sent to Unity, does this one is 3D ? ")}),  

	@variable(name = AbstractUnityLinker.BACKGROUND_GEOMS_NAMES, type = IType.LIST, of =  IType.STRING, 
			doc = { @doc ("For each geometry sent to Unity, its name in unity ")}), 
	@variable(name = AbstractUnityLinker.BACKGROUND_GEOMS_TAGS, type = IType.LIST, of =  IType.STRING, 
	doc = { @doc ("For each geometry sent to Unity, its tag and layer in unity ")}), 

	@variable(name = AbstractUnityLinker.DO_SEND_WORLD, type = IType.BOOL, init="true", 
			doc = { @doc ("Has the agents has to be sent to unity?")}),  
	
	@variable(name = AbstractUnityLinker.INITIALIZED, type = IType.BOOL, init="false", 
	doc = { @doc ("Has the world being initialized yet?")}),  

	@variable(name = AbstractUnityLinker.PLAYER_SPECIES, type = IType.STRING, 
	doc = { @doc ("Species of the player agent")}),  
	
	@variable(name = AbstractUnityLinker.RECEIVE_INFORMATION, type = IType.BOOL, init="false", 
	doc = { @doc ("should GAMA receive information from Unity?")}),  

	@variable(name = AbstractUnityLinker.MOVE_PLAYER_EVENT, type = IType.BOOL, init="false", 
			doc = { @doc ("Does the player agent moved from GAMA?")}),
	
	@variable(name = AbstractUnityLinker.MOVE_PLAYER_FROM_UNITY, type = IType.BOOL, init="true", 
			doc = { @doc ("Has the player to move in GAMA as it moves in Unity?")}), 
	
	@variable(name = AbstractUnityLinker.USE_MIDDLEWARE, type = IType.BOOL, init="true", 
	doc = { @doc ("Use of the middleware to connect Unity and GAMA? Direct connection is only usable for 1 player game")}),  


	@variable(name = AbstractUnityLinker.USE_PHYSICS_FOR_PLAYERS, type = IType.BOOL, init="true", 
			doc = { @doc ("Does the player should has a physical exitence in Unity (i.e. cannot pass through specific geometries)?")}),  
	
	@variable(name = AbstractUnityLinker.NEW_PLAYER_POSITION, type = IType.LIST, of = IType.INT, 
			doc = { @doc ("The new poistion of the player to be sent to Unity - list of int [x,y]")}), 
	@variable(name = AbstractUnityLinker.DISTANCE_PLAYER_SELECTION, type = IType.FLOAT, init = "2.0", 
	doc = { @doc ("Maximal distance to select a player agent")}), 
@variable(name = AbstractUnityLinker.INIT_LOCATIONS, type = IType.LIST, of=  IType.POINT, 
		doc = { @doc ("Init locations of the player agents in the environment - this information will be sent to Unity to move the players accordingly")}), 

	@variable(name = AbstractUnityLinker.THE_PLAYERS, type = IType.MAP, 
	doc = { @doc ("Player agents indexes by their name")}), 
})
public class AbstractUnityLinker extends GamlAgent { 
	public static final String PLAYER_SPECIES = "player_species";
	public static final String MIN_NUMBER_PLAYERS = "min_num_players";
	public static final String MAX_NUMBER_PLAYERS = "max_num_players";
	public static final String CONNECT_TO_UNITY = "connect_to_unity";
	public static final String CLIENT = "client";
	public static final String PORT = "port";
	public static final String END_MESSAGE_SYMBOL = "end_message_symbol";
	public static final String PRECISION = "precision";
	public static final String DELAY_AFTER_MES = "delay_after_mes";
	public static final String AGENTS_TO_SEND = "agents_to_send";
	public static final String BACKGROUND_GEOMS = "background_geoms";
	public static final String BACKGROUND_GEOMS_HEIGHTS = "background_geoms_heights";
	public static final String BACKGROUND_GEOMS_COLLIDERS = "background_geoms_colliders";
	public static final String BACKGROUND_GEOMS_NAMES = "background_geoms_names";

	public static final String BACKGROUND_GEOMS_TAGS = "background_geoms_tags";
	public static final String BACKGROUND_GEOMS_3D = "background_geoms_is3D";
	public static final String DO_SEND_WORLD = "do_send_world";

	public static final String MOVE_PLAYER_EVENT = "move_player_event";
	public static final String USE_MIDDLEWARE = "use_middleware";
	public static final String MOVE_PLAYER_FROM_UNITY = "move_player_from_unity";
	public static final String USE_PHYSICS_FOR_PLAYERS = "use_physics_for_player";
	public static final String INIT_LOCATIONS = "init_locations";
	public static final String THE_PLAYERS = "player_agents";

	public static final String NEW_PLAYER_POSITION = "new_player_position";
	public static final String DISTANCE_PLAYER_SELECTION = "distance_player_selection";

	public static final String INITIALIZED = "initialized";

	public static final String RECEIVE_INFORMATION = "receive_information";

	public static final String WAITING_MESSAGE = "ready";
	
	
	public static final String HEADING = "heading";
	public static final String ADD_TO_MAP = "add_to_map";
	
	public static final String LOC_TO_SEND = "loc_to_send";
	public static final String TO_MAP = "to_map";
	public static final String SPECIES_INDEX = "index";
	
	public static final String CONTENTS = "contents";
	public static final String ID = "id";
	public static final String CONTENT_MESSAGE = "contents";

	public static final String OUTPUT = "output";
	public static final String TYPE = "type";
	public static final String SERVER = "server";

	private IMap currentMessage;
	
	
	
	@getter (AbstractUnityLinker.DISTANCE_PLAYER_SELECTION)
	public static Double getDistanceSelection(final IAgent agent) {
		return (Double) agent.getAttribute(DISTANCE_PLAYER_SELECTION);
	}
	@setter(AbstractUnityLinker.DISTANCE_PLAYER_SELECTION)
	public static void setDistanceSelection(final IAgent agent, final Double val) {
		agent.setAttribute(DISTANCE_PLAYER_SELECTION, val);
	}
	
		
	@getter (AbstractUnityLinker.CONNECT_TO_UNITY)
	public static Boolean getConnectToUnity(final IAgent agent) {
		return (Boolean) agent.getAttribute(CONNECT_TO_UNITY);
	}
	@setter(AbstractUnityLinker.CONNECT_TO_UNITY)
	public static void setConnectToUnity(final IAgent agent, final Boolean ctu) {
		agent.setAttribute(CONNECT_TO_UNITY, ctu);
	}
	
	
	@getter (AbstractUnityLinker.USE_MIDDLEWARE)
	public static Boolean getUseMiddleware(final IAgent agent) {
		return (Boolean) agent.getAttribute(USE_MIDDLEWARE);
	}
	@setter(AbstractUnityLinker.USE_MIDDLEWARE)
	public static void setUseMiddleware(final IAgent agent, final Boolean ctu) {
		agent.setAttribute(USE_MIDDLEWARE, ctu);
	}
		
	@getter (AbstractUnityLinker.MIN_NUMBER_PLAYERS)
	public static Integer getMinPlayer(final IAgent agent) {
		return (Integer) agent.getAttribute(MIN_NUMBER_PLAYERS);
	}
	@setter(AbstractUnityLinker.MIN_NUMBER_PLAYERS)
	public static void setMinPlayer(final IAgent agent, final Integer val) {
		agent.setAttribute(MIN_NUMBER_PLAYERS, val);
	}
	
		
	@getter (AbstractUnityLinker.MAX_NUMBER_PLAYERS)
	public static Integer getMaxPlayer(final IAgent agent) {
		return (Integer) agent.getAttribute(MAX_NUMBER_PLAYERS);
	}
	@setter(AbstractUnityLinker.MAX_NUMBER_PLAYERS)
	public static void setMaxPlayer(final IAgent agent, final Integer val) {
		agent.setAttribute(MAX_NUMBER_PLAYERS, val);
	}
	
	@getter (AbstractUnityLinker.PORT)
	public static Integer getPort(final IAgent agent) {
		return (Integer) agent.getAttribute(PORT);
	}
	@setter(AbstractUnityLinker.PORT)
	public static void setPort(final IAgent agent, final Integer val) {
		agent.setAttribute(PORT, val);
	}
	
	/*@getter (AbstractUnityLinker.END_MESSAGE_SYMBOL)
	public static String getEndMessageSymbol(final IAgent agent) {
		return (String) agent.getAttribute(END_MESSAGE_SYMBOL);
	}
	@setter(AbstractUnityLinker.END_MESSAGE_SYMBOL)
	public static void setEndMessageSymbol(final IAgent agent, final String val) {
		agent.setAttribute(END_MESSAGE_SYMBOL, val);
	}*/
	
	@getter (AbstractUnityLinker.PRECISION)
	public static Integer getPrecision(final IAgent agent) {
		return (Integer) agent.getAttribute(PRECISION);
	}
	@setter(AbstractUnityLinker.PRECISION)
	public static void setPrecision(final IAgent agent, final Integer val) {
		agent.setAttribute(PRECISION, val);
	}
	
	/*@getter (AbstractUnityLinker.DELAY_AFTER_MES)
	public static Double getDelayAfterMes(final IAgent agent) {
		return (Double) agent.getAttribute(DELAY_AFTER_MES);
	}
	@setter(AbstractUnityLinker.DELAY_AFTER_MES)
	public static void setDelayAfterMes(final IAgent agent, final Double val) {
		agent.setAttribute(DELAY_AFTER_MES, val);
	}
	
	@getter (AbstractUnityLinker.WAITING_MESSAGE)
	public static String getWaitingMessage(final IAgent agent) {
		return (String) agent.getAttribute(WAITING_MESSAGE);
	}
	@setter(AbstractUnityLinker.WAITING_MESSAGE)
	public static void setWaitingMessage(final IAgent agent, final String val) {
		agent.setAttribute(WAITING_MESSAGE, val);
	}*/
	
	@getter (AbstractUnityLinker.AGENTS_TO_SEND)
	public static  IList<IAgent> getAgentsToSend(final IAgent agent) {
		return ( IList<IAgent>) agent.getAttribute(AGENTS_TO_SEND);
	}
	@setter(AbstractUnityLinker.AGENTS_TO_SEND)
	public static void setAgentsToSend(final IAgent agent, final IList<IAgent> val) {
		agent.setAttribute(AGENTS_TO_SEND, val);
	}
		
	@getter (AbstractUnityLinker.BACKGROUND_GEOMS)
	public static  IList<IShape> getBackgroundGeoms(final IAgent agent) {
		return ( IList<IShape>) agent.getAttribute(BACKGROUND_GEOMS);
	}
	@setter(AbstractUnityLinker.BACKGROUND_GEOMS)
	public static void setBackgroundGeoms(final IAgent agent, final IList<IShape> val) {
		agent.setAttribute(BACKGROUND_GEOMS, val);
	}
	
	@getter (AbstractUnityLinker.BACKGROUND_GEOMS_HEIGHTS)
	public static  IList<Double> getBackgroundGeomsHeights(final IAgent agent) {
		return ( IList<Double>) agent.getAttribute(BACKGROUND_GEOMS_HEIGHTS);
	}
	@setter(AbstractUnityLinker.BACKGROUND_GEOMS_HEIGHTS)
	public static void setBackgroundGeomsHeights(final IAgent agent, final IList<Double> val) {
		agent.setAttribute(BACKGROUND_GEOMS_HEIGHTS, val);
	}
		
	@getter (AbstractUnityLinker.BACKGROUND_GEOMS_COLLIDERS)
	public static  IList<Boolean> getBackgroundGeomsColliders(final IAgent agent) {
		return ( IList<Boolean>) agent.getAttribute(BACKGROUND_GEOMS_COLLIDERS);
	}
	@setter(AbstractUnityLinker.BACKGROUND_GEOMS_COLLIDERS)
	public static void setBackgroundGeomsColliders(final IAgent agent, final IList<Boolean> val) {
		agent.setAttribute(BACKGROUND_GEOMS_COLLIDERS, val);
	}
	
	@getter (AbstractUnityLinker.BACKGROUND_GEOMS_3D)
	public static  IList<Boolean> getBackgroundGeoms3D(final IAgent agent) {
		return ( IList<Boolean>) agent.getAttribute(BACKGROUND_GEOMS_3D);
	}
	@setter(AbstractUnityLinker.BACKGROUND_GEOMS_3D)
	public static void setBackgroundGeoms3D(final IAgent agent, final IList<Boolean> val) {
		agent.setAttribute(BACKGROUND_GEOMS_3D, val);
	}
		

		
	@getter (AbstractUnityLinker.BACKGROUND_GEOMS_NAMES)
	public static  IList<String> getBackgroundGeomsNames(final IAgent agent) {
		return ( IList<String>) agent.getAttribute(BACKGROUND_GEOMS_NAMES);
	}
	@setter(AbstractUnityLinker.BACKGROUND_GEOMS_NAMES)
	public static void setBackgroundGeomsNames(final IAgent agent, final IList<String> val) {
		agent.setAttribute(BACKGROUND_GEOMS_NAMES, val);
	}
	
	@getter (AbstractUnityLinker.BACKGROUND_GEOMS_TAGS)
	public static  IList<String> getBackgroundGeomsTags(final IAgent agent) {
		return ( IList<String>) agent.getAttribute(BACKGROUND_GEOMS_TAGS);
	}
	@setter(AbstractUnityLinker.BACKGROUND_GEOMS_TAGS)
	public static void setBackgroundGeomsTags(final IAgent agent, final IList<String> val) {
		agent.setAttribute(BACKGROUND_GEOMS_TAGS, val);
	}
	
	@getter (AbstractUnityLinker.PLAYER_SPECIES)
	public static String getPlayerSpecies(final IAgent agent) {
		return (String) agent.getAttribute(PLAYER_SPECIES);
	}
	@setter(AbstractUnityLinker.PLAYER_SPECIES)
	public static void setPlayerSpecies(final IAgent agent, final String val) {
		agent.setAttribute(PLAYER_SPECIES, val);
	}
	
	@getter (AbstractUnityLinker.DO_SEND_WORLD)
	public static Boolean getDoSendWorld(final IAgent agent) {
		return (Boolean) agent.getAttribute(DO_SEND_WORLD);
	}
	@setter(AbstractUnityLinker.DO_SEND_WORLD)
	public static void setDoSendWorld(final IAgent agent, final Boolean val) {
		agent.setAttribute(DO_SEND_WORLD, val);
	}
	
	@getter (AbstractUnityLinker.RECEIVE_INFORMATION)
	public static Boolean getReceiveInformation(final IAgent agent) {
		return (Boolean) agent.getAttribute(RECEIVE_INFORMATION);
	}
	@setter(AbstractUnityLinker.RECEIVE_INFORMATION)
	public static void setReceiveInformation(final IAgent agent, final Boolean val) {
		agent.setAttribute(RECEIVE_INFORMATION, val);
	}
	
	@getter (AbstractUnityLinker.INITIALIZED)
	public static Boolean getInitialized(final IAgent agent) {
		return (Boolean) agent.getAttribute(INITIALIZED);
	}
	@setter(AbstractUnityLinker.INITIALIZED)
	public static void setInitialized(final IAgent agent, final Boolean val) {
		agent.setAttribute(INITIALIZED, val);
	}
		
	@getter (AbstractUnityLinker.MOVE_PLAYER_EVENT)
	public static Boolean getMovePlayerEvent(final IAgent agent) {
		return (Boolean) agent.getAttribute(MOVE_PLAYER_EVENT);
	}
	@setter(AbstractUnityLinker.MOVE_PLAYER_EVENT)
	public static void setMovePlayerEvent(final IAgent agent, final Boolean val) {
		agent.setAttribute(MOVE_PLAYER_EVENT, val);
	}
		
	@getter (AbstractUnityLinker.MOVE_PLAYER_FROM_UNITY)
	public static Boolean getMovePlayerFromUnity(final IAgent agent) {
		return (Boolean) agent.getAttribute(MOVE_PLAYER_FROM_UNITY);
	}
	@setter(AbstractUnityLinker.MOVE_PLAYER_FROM_UNITY)
	public static void setMovePlayerFromUnity(final IAgent agent, final Boolean val) {
		agent.setAttribute(MOVE_PLAYER_FROM_UNITY, val);
	}
	
	@getter (AbstractUnityLinker.USE_PHYSICS_FOR_PLAYERS)
	public static Boolean getUsePhysicsForPlayer(final IAgent agent) {
		return (Boolean) agent.getAttribute(USE_PHYSICS_FOR_PLAYERS);
	}
	@setter(AbstractUnityLinker.USE_PHYSICS_FOR_PLAYERS)
	public static void setUsePhysicsForPlayer(final IAgent agent, final Boolean val) {
		agent.setAttribute(USE_PHYSICS_FOR_PLAYERS, val);
	}
	
	
	@getter (AbstractUnityLinker.INIT_LOCATIONS)
	public static IList<GamaPoint> getPlayerLocationInit(final IAgent agent) {
		return (IList<GamaPoint>) agent.getAttribute(INIT_LOCATIONS);
	}
	@setter(AbstractUnityLinker.INIT_LOCATIONS)
	public static void setPlayerLocationInit(final IAgent agent, final IList val) {
		agent.setAttribute(INIT_LOCATIONS, val);
	}
	
	@getter (AbstractUnityLinker.THE_PLAYERS)
	public static IMap<String, IAgent> getPlayers(final IAgent agent) {
		return (IMap<String, IAgent>) agent.getAttribute(THE_PLAYERS);
	}
	@setter(AbstractUnityLinker.THE_PLAYERS)
	public static void setPlayers(final IAgent agent, final IMap<String, IAgent> val) {
		agent.setAttribute(THE_PLAYERS, val);
	}
		
	
	@getter (AbstractUnityLinker.NEW_PLAYER_POSITION)
	public static  IList<Integer> getNewPlayerPosition(final IAgent agent) {
		return ( IList<Integer>) agent.getAttribute(NEW_PLAYER_POSITION);
	}
	@setter(AbstractUnityLinker.NEW_PLAYER_POSITION)
	public static void setNewPlayerPosition(final IAgent agent, final IList<Integer> val) {
		agent.setAttribute(NEW_PLAYER_POSITION, val);
	}
	
	public AbstractUnityLinker(IPopulation<? extends IAgent> s, int index) {
		super(s, index);
		
	}
	
	@Override
	public void dispose() {
		
		super.dispose();
	}

	
	private Object doActionNoArg(IScope scope, String actionName) {
		WithArgs act = getAgent().getSpecies().getAction(actionName);
		return act.executeOn(scope);
	}
	
	private Object doAction1Arg(IScope scope, String actionName, String argName, Object ArgVal ) {
		Arguments args = new Arguments();
		args.put(argName, ConstantExpressionDescription.create(ArgVal));
		WithArgs act = getAgent().getSpecies().getAction(actionName);
		act.setRuntimeArgs(scope, args);
		return act.executeOn(scope);
	}
	
	private Object doAction2Arg(IScope scope, String actionName, String argName1, Object ArgVal1, String argName2, Object ArgVal2 ) {
		Arguments args = new Arguments();
		args.put(argName1, ConstantExpressionDescription.create(ArgVal1));
		args.put(argName2, ConstantExpressionDescription.create(ArgVal2));
		WithArgs act = getAgent().getSpecies().getAction(actionName);
		act.setRuntimeArgs(scope, args);
		return act.executeOn(scope);
	}
	
	
	
	

	@Override
	public Object _init_(IScope scope) {
		Object init = super._init_(scope);
		startSimulation(scope);
		return init ;
	}
	
	private void interactionWithPlayer(final IScope scope, IAgent ag) {
		GamaPoint pt = scope.getGui().getMouseLocationInModel();
		if (pt != null) {
			IShape geom = Creation.circle(scope, getDistanceSelection(ag), pt);
			IList<IAgent> ags =  (IList<IAgent>) Queries.overlapping(scope,getPlayers(ag), geom);
			if (ags.isEmpty()) {
				Optional<IAgent> selected = getPlayers(ag).getValues().stream().filter (a -> (Boolean) a.getAttribute("selected")).findFirst(); 
				if (selected.isPresent()) {
					doAction2Arg(scope, "move_player", "player", selected.get(), "loc", pt) ;
				}
			} else {
				IAgent player = (IAgent) Queries.closest_to(scope, ags, pt);
				player.setAttribute(AbstractUnityPlayer.SELECTED, !((Boolean)player.getAttribute(AbstractUnityPlayer.SELECTED)));
			}
		} 
	}
	
 	@Override
	public boolean doStep(final IScope scope) {
		if (super.doStep(scope)) {
			IAgent ag = getAgent();
			setInitialized(ag, true);
			if (getConnectToUnity(ag)) {
				if (getInitialized(ag) && getMovePlayerEvent(ag) && !getPlayers(ag).isEmpty()) {
					setMovePlayerEvent(ag, false);
					interactionWithPlayer(scope, ag);
				}
				//doActionNoArg(scope, "manage_message_from_unity");
				if(getInitialized(ag)) {
					if (getDoSendWorld(ag)) {
						doActionNoArg(scope, "send_world");
					}
					
				}

				if (currentMessage != null && !currentMessage.isEmpty()) {
					sendCurrentMessage(scope);
				}
				
			}
			return true;
		}
		return false;
	}

	
	
	private void addBackgroundGeometries(IList geoms, IList names, Double height, Boolean collider, String tag, Boolean is3D) {
		IList<IShape> backgroundGeometries = getBackgroundGeoms(getAgent());
		IList<Double> backgroundGeometriesHeight = getBackgroundGeomsHeights(getAgent());
		IList<String> backgroundGeometriesName = getBackgroundGeomsNames(getAgent());
		IList<Boolean> backgroundGeometriesCollider = getBackgroundGeomsColliders(getAgent());
		IList<String> backgroundGeometriesTag = getBackgroundGeomsTags(getAgent());
		IList<Boolean> backgroundGeometriesis3D = getBackgroundGeoms3D(getAgent());
		
		
		if(backgroundGeometries == null) {
			backgroundGeometries = GamaListFactory.create(Types.GEOMETRY);
			backgroundGeometriesHeight = GamaListFactory.create(Types.FLOAT);
			backgroundGeometriesName = GamaListFactory.create(Types.STRING);
			backgroundGeometriesTag = GamaListFactory.create(Types.STRING);
			backgroundGeometriesCollider = GamaListFactory.create(Types.BOOL);
			backgroundGeometriesis3D = GamaListFactory.create(Types.BOOL);
		}
		backgroundGeometries.addAll(geoms);
		
		for (int i = 0; i < geoms.size(); i++) {
			backgroundGeometriesHeight.add(height);
			backgroundGeometriesCollider.add(collider);
			if (tag != null) 
				backgroundGeometriesTag.add(tag);
			if (is3D != null)
				backgroundGeometriesis3D.add(is3D);
			else backgroundGeometriesis3D.add(true);
		}
		if (names != null) 
			backgroundGeometriesName.addAll(names);
		
		setBackgroundGeoms(getAgent(), backgroundGeometries);
		setBackgroundGeomsHeights(getAgent(), backgroundGeometriesHeight);
		setBackgroundGeomsColliders(getAgent(), backgroundGeometriesCollider);
		setBackgroundGeomsNames(getAgent(), backgroundGeometriesName);
		setBackgroundGeomsTags(getAgent(), backgroundGeometriesTag);
		setBackgroundGeoms3D(getAgent(), backgroundGeometriesis3D);
	
	}
	
	@action (
			name = "init_species_to_send",
			args = { @arg (
							name = "species_list",
							type = IType.LIST, 
							doc = @doc ("List of the species name to sent to unity"))},
					
			doc = { @doc (
					value = "Initialize the species to send to unity")})
	
	public void primInitSpecies(final IScope scope) throws GamaRuntimeException {
		IList sps = scope.getListArg("species_list");
		int i = 0;
		IList<IAgent> agensToSend = getAgentsToSend(getAgent());
		for(Object s : sps) {
			ISpecies sp = Cast.asSpecies(scope, s);
			
			for (IAgent ag : sp.getAgents(scope).iterable(scope)) {
				ag.setAttribute(SPECIES_INDEX, i);
				agensToSend.add(ag);
			}
			i++;
		}		
	}
	
	private void sendCurrentMessage(IScope scope) {
		PlatformAgent pa = GAMA.getPlatformAgent();
		if (getUseMiddleware(getAgent())) {
			String mes = SerialisationOperators.toJson(scope, currentMessage, false);
			
			pa.sendMessage(scope,ConstantExpressionDescription.create(mes));
		} else {

		//	System.out.println("num mes: " + currentMessage.size());
			for (IMap v : (IList<IMap>) currentMessage.get(CONTENTS)) {
				Object c = v.get(CONTENT_MESSAGE);
				String mes = SerialisationOperators.toJson(scope, c, false);
				pa.sendMessage(scope,ConstantExpressionDescription.create(mes));
			}
		}
		
		currentMessage.clear();
		
	}
	
	
	private void addToCurrentMessage(IScope scope, IList<String> recipients, Object content ) {
		if (currentMessage == null) {
			currentMessage = GamaMapFactory.create();
		}
		if (currentMessage.isEmpty()) {
			currentMessage.put(CONTENTS, GamaListFactory.create());
			currentMessage.put(TYPE, OUTPUT);
		}
		IMap newMessage = GamaMapFactory.create();
		newMessage.put(ID, recipients);
		newMessage.put(CONTENT_MESSAGE, content);
		((IList) currentMessage.get(CONTENTS)).add(newMessage);
	}
	
	@action (
			name = "send_message",
			args = {
					 @arg (name = "players",
								type = IType.LIST,
								doc = @doc ("Players to send the geometries to")),
					 @arg (name = "mes",
							type = IType.MAP, 
							doc = @doc ("Map to send"))},
			doc = { @doc (
					value = "send a message to the Unity Client")})
	public void primSentMessage(final IScope scope) throws GamaRuntimeException {
		IAgent ag = getAgent();
		IList<IAgent> players = (IList) scope.getListArg("players");
		IMap mes = (IMap) scope.getArg("mes");
		
		
		IMap message = GamaMapFactory.create();
		message.put(CONTENTS, GamaListFactory.create());
		message.put(TYPE, OUTPUT);
		
		IMap newMessage = GamaMapFactory.create();
		
		IList<String> recipients = GamaListFactory.create();
		for(IAgent a : players) {
			recipients.add(a.getName());
		}
		newMessage.put(ID, recipients);
		newMessage.put(CONTENT_MESSAGE, mes);
		((IList) message.get(CONTENTS)).add(newMessage);
		
		//mes += getEndMessageSymbol(ag);
		PlatformAgent pa = GAMA.getPlatformAgent();
		String mesStr = SerialisationOperators.toJson(scope, message, false);
		
		pa.sendMessage(scope,ConstantExpressionDescription.create(mesStr));
		//argsS.put("contents", ConstantExpressionDescription.create(mes));
		
		//WithArgs actS = getAgent().getSpecies().getAction("send");
		//actS.setRuntimeArgs(scope, argsS);
		//actS.executeOn(scope);
	}
	/*@action (
			name = "add_to_send_world",
					args = {@arg (
							name = "map_to_send",
							type = IType.MAP,
							doc = @doc ("data already sent to the client"))},
			doc = { @doc (
					value = "add values to the current state of the world to the Unity Client")})
	public void primAddToSentWorld(final IScope scope) throws GamaRuntimeException {
		
	}
		*/
		
	@action (
			name = "send_world",
					
					
			doc = { @doc (
					value = "send the current state of the world to the Unity Client")})
	public void primSentWorld(final IScope scope) throws GamaRuntimeException {
		IAgent ag = getAgent();
		IMap<String, Object> toSend = GamaMapFactory.create();
		
		IList<IAgent> ags = GamaListFactory.create();
		ags.addAll(getAgentsToSend(ag).stream().filter(a -> !a.dead()).toList());
		for (IAgent player : getPlayers(ag).values()) {
			if (((Double) player.getAttribute(AbstractUnityPlayer.PLAYER_AGENTS_PERCEPTION_RADIUS)) > 0) {
				ags = (IList<IAgent>) doAction1Arg(scope, "filter_distance", "ags", ags);
			}
			if (((Double) player.getAttribute(AbstractUnityPlayer.PLAYER_AGENTS_MIN_DIST)) > 0) {
				ags = (IList<IAgent>) doAction1Arg(scope, "filter_overlapping", "ags", ags);
			} 
			
	 
			IList<IMap> messageAgs = (IList<IMap>) doAction1Arg(scope, "message_agents", "ags", ags);
			toSend.put("agents", messageAgs);
		//	toSend.put("position", getNewPlayerPosition(ag));
//			doAction1Arg(scope, "add_to_send_world", "map_to_send", toSend );
			setNewPlayerPosition(ag, GamaListFactory.create()); 
			IList<String> rec = GamaListFactory.create();
			rec.add(player.getName());
			
			addToCurrentMessage(scope,rec, toSend);
			//sendMessage(scope, toSend,  player); 
			doAction1Arg(scope, "after_sending_world", "map_to_send", toSend);
		}

		
	}
		
	@action (
			name = "send_geometries",
					args = {@arg (
							name = "geoms",
							type = IType.LIST,
							doc = @doc ("List of geometries to send")),
							 @arg (
							name = "heights",
							type = IType.LIST,
							doc = @doc ("List of heights (float) associated to each geometry")),
							 @arg (name = "players",
								type = IType.LIST,
								doc = @doc ("Players to send the geometries to")),
								
							 @arg (
										name = "geometry_colliders",
										type = IType.LIST,
										doc = @doc ("For each geometry, does a collider has to be instanciated (list of bools) ")),
							 @arg (
										name = "is_3D",
										type = IType.LIST,
										doc = @doc ("For each geometry, is a 3D geometries (list of bools) ")),
							 @arg (
										name = "names",
										type = IType.LIST,
										doc = @doc ("List of name (string) associated to each geometry")),
							 @arg (
										name = "tags",
										type = IType.LIST,
										doc = @doc ("List of tags (string) associated to each geometry"))},
			doc = { @doc (
					value = "send the background geometries to the Unity client")})
	public void primSentGeometries(final IScope scope) throws GamaRuntimeException {
		IAgent ag = getAgent();
		IList<IAgent> players = (IList) scope.getListArg("players");
		IMap<String, Object> toSend = GamaMapFactory.create();
		IList<Object> points = GamaListFactory.create();
		int precision = getPrecision(ag); 
		
		IList<IShape> geoms = scope.getListArg("geoms");
		IList<Double> heights = scope.getListArg("heights");
		IList<Boolean> geometry_colliders = scope.getListArg("geometry_colliders");
		IList<String> names = scope.getListArg("names");
		IList<String> tags = scope.getListArg("tags");
		IList<Boolean> are3D = scope.getListArg("is_3D");
		
		for (IShape g : geoms ) {
			for (GamaPoint pt : g.getPoints()) {
				IMap<String, Object> ptM = GamaMapFactory.create();
				IList<Integer> ptL = GamaListFactory.create(Types.INT);
				ptL.add((int)(pt.x*precision));
				ptL.add((int)(pt.y*precision));
				ptM.put("c", ptL);
				points.add(ptM);
			}
			IMap<String, Object> ptM = GamaMapFactory.create();
			ptM.put("c", GamaListFactory.create());
			points.add(ptM);
		}
		toSend.put("points", points);
		toSend.put("heights", heights);
		toSend.put("hasColliders", geometry_colliders);
		toSend.put("names", names);
		toSend.put("tags", tags);
		toSend.put("is3D", are3D);
		IList<String> playersStr = GamaListFactory.create();
		for (IAgent pl : players) 
			playersStr.add(pl.getName());
		addToCurrentMessage(scope, playersStr,toSend);
	//	sendMessage(scope, toSend, player);
		
		doAction1Arg(scope, "after_sending_geometries", "players", players);	
		
	}
	
	/*private void sendMessage(IScope scope,IMap toSend, IAgent player) {
		IAgent ag = getAgent();
		Arguments argsS = new Arguments();
		argsS.put("to", ConstantExpressionDescription.create(player.getAttribute(AbstractUnityPlayer.UNITY_CLIENT)));
		String mes = Containers.asJsonString(scope, toSend) + getEndMessageSymbol(ag);
		argsS.put("contents", ConstantExpressionDescription.create(mes));
		
		WithArgs actS = getAgent().getSpecies().getAction("send");
		actS.setRuntimeArgs(scope, argsS);
		actS.executeOn(scope);
	}*/
	

	
	@action (
			name = "init_server",
			doc = { @doc (
					value = "initialize the Gama server")})
	public Boolean primInitServer(final IScope scope) throws GamaRuntimeException {
		WithArgs actConnect = getAgent().getSpecies().getAction(INetworkSkill.CONNECT_TOPIC);
		Arguments args = new Arguments();
//		args.put("protocol", ConstantExpressionDescription.create("tcp_server"));
		args.put("protocol", ConstantExpressionDescription.create("websocket_server"));
		args.put("port", ConstantExpressionDescription.create(getPort(getAgent())));
		args.put("raw", ConstantExpressionDescription.create(true));
		args.put("with_name", ConstantExpressionDescription.create("server" ));
		args.put("force_network_use", ConstantExpressionDescription.create(true));
		actConnect.setRuntimeArgs(scope, args);
		return (Boolean) actConnect.executeOn(scope);
	}	
	
	@action (
			name = "send_init_data",
					args = { 
							 @arg (name = "player",
								type = IType.AGENT,
								doc = @doc ("Player to send the geometries to"))},
			doc = { @doc (
					value = "Wait for the connection of a unity client and send the paramters to the client")})
	public void primSentInitData(final IScope scope) throws GamaRuntimeException {
		
		IAgent player = (IAgent) scope.getArg("player");
		
		doAction1Arg(scope, "send_parameters", "player", player );
		//System.out.println("send_parameters ");
		if (!getBackgroundGeoms(getAgent()).isEmpty()) {
			WithArgs actSG = scope.getAgent().getSpecies().getAction( "send_geometries");
			//System.out.println("send_geometries ");
			
			Arguments argsSG = new Arguments();
			argsSG.put("geoms", ConstantExpressionDescription.create(getBackgroundGeoms(getAgent())));
			argsSG.put("heights", ConstantExpressionDescription.create(getBackgroundGeomsHeights(getAgent())));
			argsSG.put("geometry_colliders", ConstantExpressionDescription.create(getBackgroundGeomsColliders(getAgent())));
			argsSG.put("is_3D", ConstantExpressionDescription.create(getBackgroundGeoms3D(getAgent())));
			argsSG.put("tags", ConstantExpressionDescription.create(getBackgroundGeomsTags(getAgent())));
			
			IList<IAgent> pls = GamaListFactory.create();
			pls.add(player);
			argsSG.put("players", ConstantExpressionDescription.create(pls));
			
			argsSG.put("names", ConstantExpressionDescription.create(getBackgroundGeomsNames(getAgent())));
			actSG.setRuntimeArgs(scope, argsSG);
			actSG.executeOn(scope);
			
			
		}
		if (getDoSendWorld(getAgent())) {
			 doActionNoArg(scope, "send_world" );
		}
		startSimulation(scope);
	}
	
	@action (
			name = "after_sending_world",
			args = {@arg (
					name = "map_to_send",
					type = IType.MAP,
					doc = @doc ("data already sent to the client"))},
			doc = { @doc (
					value = "Action trigger just after sending the world to Unity ")})
	public void primAfterSendingWorld(final IScope scope) throws GamaRuntimeException {
	
	}
	
	@action (
			name = "after_sending_geometries",
			
			doc = { @doc (
					value = "Action trigger just after sending the background geometries to Unity ")})
	public void primAfterSendingGeometries(final IScope scope) throws GamaRuntimeException {
	
	}
	
	@action (
			name = "create_player",
					args = { @arg (
							name = "id",
							type = IType.STRING,
							doc = @doc ("name of the player agent"))/*,
							@arg (
									name = "client",
									type = IType.STRING,
									doc = @doc ("client of the player agent"))*/},
							
				
			doc = { @doc (
					value = "Create a new unity player agent")})
	public void primInitPlayer(final IScope scope) throws GamaRuntimeException {
	//System.out.println("ICI : primInitPlayer");
		IMap<String, IAgent> players = getPlayers(getAgent());
		IAgent ag = getAgent();
		String id = scope.getStringArg("id");
		//Object client = scope.getArg("client");
		
		ISpecies sp = Cast.asSpecies(scope, getPlayerSpecies(ag));
	//	System.out.println("sp: " + sp);
		if (sp == null) return;
		if (getMaxPlayer(ag) >= 0 && (getPlayers(ag).length(scope) >= getMaxPlayer(ag))) return;
		if (getPlayers(ag).containsKey(id)) {
			return;
		}
		Map<String, Object>  init = GamaMapFactory.create();
		if (getPlayerLocationInit(ag).size() <= players.length(scope)) {
			getPlayerLocationInit(ag).add(Punctal.any_location_in(scope, scope.getSimulation()));
		}
		init.put(IKeyword.LOCATION, getPlayerLocationInit(ag).get(players.length(scope)));
		init.put(IKeyword.NAME, id);
		
		IAgent player = sp.getPopulation(scope).createAgentAt(scope, 0, init, false, true);
		
		/*player.setAttribute(AbstractUnityPlayer.UNITY_CLIENT, client);*/
		//doAction1Arg(scope, "send_init_data", "player", player);
		getPlayers(getAgent()).put(id, player);
		//System.out.println("getPlayers(getAgent()): " + getPlayers(getAgent()) + " id : " + id + " player: " + player);
	}
	
	@action (
			name = "filter_distance",
			args = { @arg (
					name = "ags",
					type = IType.LIST,
					doc = @doc ("list of agents to filter")),
					 @arg (name = "player",
								type = IType.AGENT,
								doc = @doc ("the player agent"))},
					
			doc = { @doc (
					value = "Action called by the send_world action that returns the sub-list of agents to send to Unity from a given list of agents according to a max distance to the player")})
	public IList<IAgent> primFilterDistance(final IScope scope) throws GamaRuntimeException {
		IAgent player= (IAgent) scope.getArg("player");
		IList<IAgent> ags = GamaListFactory.create(Types.AGENT);
		ags.addAll((IList<IAgent>) scope.getArg("ags"));
		
		Double dist = (Double) player.getAttribute(AbstractUnityPlayer.PLAYER_AGENTS_PERCEPTION_RADIUS);
		return (IList<IAgent>) Spatial.Queries.overlapping(scope, ags, Transformations.enlarged_by(scope, player, dist));
	}
	 
	@action (
			name = "message_agents",
			args = { @arg (
					name = "ags",
					type = IType.LIST,
					doc = @doc ("list of agents to send to Unity"))},
					
			doc = { @doc (
					value = "Action called by the send_world action that returns the message to send to Unity (as a list of map)")})
	public IList<IMap> primMessageAgents(final IScope scope) throws GamaRuntimeException {
		IList<IAgent> ags = GamaListFactory.create(Types.AGENT);
		ags.addAll((IList<IAgent>) scope.getArg("ags"));
		IList<IMap> output = GamaListFactory.create(Types.MAP);
		for (IAgent ag : ags) {
			WithArgs actTM = getAgent().getSpecies().getAction(TO_MAP);
			Arguments argsTM = new Arguments();
			argsTM.put("precision", ConstantExpressionDescription.create(getPrecision(getAgent())));
			argsTM.put("ag", ConstantExpressionDescription.create(ag));
			actTM.setRuntimeArgs(scope, argsTM);
			
			output.add((IMap) actTM.executeOn(scope));
		}
		return output;
	}
	
	@action (
			name = "filter_overlapping",
			args = { @arg (
					name = "ags",
					type = IType.LIST,
					doc = @doc ("list of agents to filter")),
			 @arg (name = "player",
				type = IType.AGENT,
				doc = @doc ("the player agent"))},
					
			doc = { @doc (
					value = "Action called by the send_world action that returns the sub-list of agents to send to Unity from a given list of agents according to a min proximity to the other agents to send")})
	public IList<IAgent> primFilterOverlapping(final IScope scope) throws GamaRuntimeException {
		IList<IAgent> ags = GamaListFactory.create(Types.AGENT);
		ags.addAll((IList<IAgent>) scope.getArg("ags"));
		
		IAgent thePlayer = (IAgent) scope.getArg("player");
		IList<IAgent> toRemove = GamaListFactory.create() ;
		for (IShape ag : ags) {
			if (!toRemove.contains(ag)) { 
				Double dist = (Double) thePlayer.getAttribute(AbstractUnityPlayer.PLAYER_AGENTS_MIN_DIST);
				toRemove.addAll((Collection<? extends IAgent>) Spatial.Queries.overlapping(scope, ags, Transformations.enlarged_by(scope, thePlayer, dist)));
			}  
		}
		ags.removeAll(toRemove);
		return ags;
	}
	
	
	@action (
			name = "new_player_location",
			args = { @arg (
					name = "loc",
					type = IType.POINT,
					doc = @doc ("Location of the player agent")),
					
					@arg (
							name = "player",
							type = IType.AGENT,
							doc = @doc ("the player agent"))},
					
			doc = { @doc (
					value = "Action called by the move_player action that returns the location to send to Unity from a given player location")})
	public GamaPoint primNewPlayerLoc(final IScope scope) throws GamaRuntimeException {
		return (GamaPoint) scope.getArg("loc");
	}
	
	@action (
			name = "move_player",
					args = { 
						@arg (name = "player",
						type = IType.AGENT,
						doc = @doc ("the player agent to move")),
						
						@arg (
								name = "loc",
								type = IType.POINT,
								doc = @doc ("Location of the player agent"))},
			doc = { @doc (
					value = "move the player agent")})
	public void primMovePlayer(final IScope scope) throws GamaRuntimeException {
		IAgent ag = getAgent();
		IAgent player = (IAgent) scope.getArg("player");
		GamaPoint loc = (GamaPoint) scope.getArg("loc");
		loc = (GamaPoint)doAction2Arg(scope, "new_player_location", "player", player, "loc", loc);
		player.setLocation(loc);
		doAction1Arg(scope, "send_player_position", "player", player);
		
	}
	
	
	@action (
			name = "move_player_external",
					args = { 
						@arg (name = "player",
						type = IType.STRING,
						doc = @doc ("the player agent to move")),
						
						@arg (
								name = "x",
								type = IType.INT,
								doc = @doc ("x Location of the player agent")),	@arg (
										name = "y",
										type = IType.INT,
										doc = @doc ("y Location of the player agent")),	@arg (
												name = "angle",
												type = IType.INT,
												doc = @doc ("angle of the player agent"))},
			doc = { @doc (
					value = "move the player agent ")})
	public void primMovePlayerFromUnity(final IScope scope) throws GamaRuntimeException {
		Integer x = scope.getIntArg("x");
		Integer y = scope.getIntArg("y");
		Integer angle = scope.getIntArg("angle");
		IAgent ag = getAgent();
		int precision = getPrecision(ag); 
		IAgent thePlayer = getPlayers(ag).get(scope.getStringArg("player")) ;
		Double rot = ((Double) thePlayer.getAttribute("player_rotation"));
		thePlayer.setAttribute("rotation", angle.floatValue()/precision + rot ); 
		thePlayer.setLocation(new GamaPoint(x.floatValue()/precision, y.floatValue()/precision));
		thePlayer.setAttribute("to_display", true);
	}

	@action (
			name = "manage_message_from_unity",
			
			doc = { @doc (
					value = "Manage messages coming from Unity")})
	public void primManageMessageFromUnity(final IScope scope) throws GamaRuntimeException {
		
		IAgent ag = getAgent();
		if (!getInitialized(ag)) {
			while(!getInitialized(ag)) {
				 doActionNoArg(scope, INetworkSkill.FETCH_MESSAGE_FROM_NETWORK);
				if ((boolean) doActionNoArg(scope, INetworkSkill.HAS_MORE_MESSAGE_IN_BOX)) {
					
					///////////////////////////////////////////////////////////////////////////////
					GamaMessage mes = (GamaMessage) doActionNoArg(scope, INetworkSkill.FETCH_MESSAGE);
					JsonObject contentMap = (JsonObject) Cast.asMap(scope, mes.getContents(scope), false).get("contents");
					
					if (contentMap.getString("type", "").equals("connection")) {
						String playerId = contentMap.getString("id", "");
						//System.out.println("Player with id " + playerId + " connected");
						doAction2Arg(scope, "create_player", "name", playerId, "client", mes.getSender());
						setInitialized(ag, (getPlayers(ag).length(scope) >= getMinPlayer(ag)));
					}
							
					////////////////////////////////////////////////////////////////////////////////
//					if ((((String) mes.getContents(scope)).contains("\"connection\":"))) {
//						String name = ((String) mes.getContents(scope)).replace("connection:", "");
//						doAction2Arg(scope, "create_player", "name", name, "client", mes.getSender());
//						setInitialized(ag, (getPlayers(ag).length(scope) >= getMinPlayer(ag)));
//					}
					
				}

				if ((getPlayers(ag).length(scope) >= getMinPlayer(ag))) break;
			}
		}
		if (getInitialized(ag)) {

			 doActionNoArg(scope, INetworkSkill.FETCH_MESSAGE_FROM_NETWORK);
			 while((boolean) doActionNoArg(scope,INetworkSkill.HAS_MORE_MESSAGE_IN_BOX)) {
				
				GamaMessage mes = (GamaMessage) doActionNoArg(scope, INetworkSkill.FETCH_MESSAGE);
				JsonObject answer = (JsonObject) Cast.asMap(scope, mes.getContents(scope), false).get("contents");
				
//				if (getWaitingMessage(ag) != null && getWaitingMessage(ag).equals(answer.getString("contents", ""))) {
//					setReceiveInformation(ag, true);
//					System.out.println("Simulation started from middleware");
//				} 
				
				if (answer.getString("contents", "").equals("ready") && !getReceiveInformation(ag)) {
					
					setReceiveInformation(ag, true);
					//System.out.println("Simulation started from middleware");
					
				} else if (!getPlayers(ag).isEmpty() && getMovePlayerFromUnity(ag) && getReceiveInformation(ag) && (((String) mes.getContents(scope)).contains("position"))) {
					
//					IList<BigDecimal> position = (IList<BigDecimal>) answer.get("position");
//					if (position != null && position.size() == 2) {
//						int precision = getPrecision(ag); 
//						for (IAgent thePlayer : getPlayers(ag)) {
//							Double rot = ((Double) thePlayer.getAttribute("player_rotation"));
//							thePlayer.setAttribute("rotation", ((BigDecimal)(answer.get("rotation"))).floatValue()/precision + rot ); 
//							thePlayer.setLocation(new GamaPoint(position.get(0).floatValue()/precision, position.get(1).floatValue()/precision));
//							thePlayer.setAttribute("to_display", true);
//						}
//					}
					//System.out.println(answer.get("contents"));
					
				} else {
					doAction1Arg(scope, "manage_new_message", "mes", mes.getContents(scope) );
				}
			}
			
		}
	}
	@action (
			name = "manage_new_message",
			args = { @arg (
				name = "mes",
				type = IType.STRING,
				doc = @doc ("message received"))},
					
			doc = { @doc (
					value = "action called by manage_message_from_unity in case of a new message (other than waiting message and player position)")})
	public void primManageNewMessage(final IScope scope) throws GamaRuntimeException {

	}
	
	/*@action (
			name = "wait_for_message",
			args = { @arg (
				name = "mes",
				type = IType.STRING,
				doc = @doc ("List of geometries to send"))},
					
			doc = { @doc (
					value = "set the message to wait")})
	public void primWaitForMessage(final IScope scope) throws GamaRuntimeException {
		String message = scope.getStringArg("mes");
		setReceiveInformation(getAgent(), false);
		setWaitingMessage(getAgent(), message);
	}*/
	
	@action (
			name = "send_player_position",
					args = { @arg (
							name = "player",
							type = IType.AGENT,
							doc = @doc ("Player agent of which to send the position"))},
			doc = { @doc (
					value = "send the new position of the player to Unity (used to teleport the player from GAMA) ")})
	public void primSendPlayerPosition(final IScope scope) throws GamaRuntimeException {
		IAgent ag = getAgent();
		if (!getConnectToUnity(ag)) {
			return; 
		}
		IAgent player = (IAgent) scope.getArg("player");
		int precision = getPrecision(ag);
		IList<Integer> pos = GamaListFactory.create();
		pos.add((int)(player.getLocation().x * precision));
		pos.add((int) (player.getLocation().y * precision));
		setNewPlayerPosition(ag, pos);
		
	
	}
	
	
	private void startSimulation(IScope scope) {
		if (getPlayers(getAgent()).size() >= getMinPlayer(getAgent())) {
			scope.getSimulation().resume(scope);
		} 
	}
	
	
	@action (
			name = "add_to_send_parameter",
					args = {@arg (
							name = "map_to_send",
							type = IType.MAP,
							doc = @doc ("data already sent to the client"))},
			doc = { @doc (
					value = "add values to the parameters sent to the Unity Client")})
	public void primAddToSentParameter(final IScope scope) throws GamaRuntimeException {
		
	}
	
	
	@action (
			name = "send_parameters",
					args = { @arg (
							name = "player",
							type = IType.NONE,
							doc = @doc ("Player to which the message will be sent"))},
								
			doc = { @doc (
					value = "Send the parameter to Unity to intialized the connection")})
	public void primSendParameters(final IScope scope) throws GamaRuntimeException {
		GamaMap<String, Object> toSend = (GamaMap<String, Object>) GamaMapFactory.create();
		IAgent ag = getAgent();
		IAgent player = (IAgent) scope.getArg("player");
		//System.out.println("PLAYER : " + player + "  players: " + getPlayers(ag));
		int precision = getPrecision(ag);
		toSend.put(PRECISION, precision);
		IList<Integer> worldT = GamaListFactory.create(Types.INT);
		worldT.add((int)(scope.getSimulation().getGeometricEnvelope().getWidth() * precision));
		worldT.add((int)(scope.getSimulation().getGeometricEnvelope().getHeight() * precision));

		toSend.put("world", worldT);
		
		//toSend.put("delay", getDelayAfterMes(ag));
		toSend.put("physics", getUsePhysicsForPlayer(ag));
		
		IList<Integer> posT = GamaListFactory.create(Types.INT);
		if (player != null) {
			posT.add((int)(player.getLocation().x * precision));
			posT.add((int)(player.getLocation().y * precision));
		} 
		toSend.put("position", posT);
		//sendMessage(scope, toSend, player);
		doAction1Arg(scope, "add_to_send_parameter", "map_to_send", toSend );
	//	System.out.println("primSendParameters toSend: " + toSend);
		addToCurrentMessage(scope, buildPlayerListfor1Player(scope, player), toSend);
	}
	
	private IList buildPlayerListfor1Player(IScope scope, IAgent player) {
		IList players = GamaListFactory.create();
		players.add(player.getName());
		return players; 
	}
	
	
	@action (
			name = "add_background_data",
			args = { @arg (
					name = "geoms",
					type = IType.LIST, 
					doc = @doc ("The list of geometry to send to Unity")),
			@arg (
				 name = "names",
				 optional = true,
				type = IType.LIST, 
				doc = @doc ("The list of names linked to the geometries to send to Unity")),
			@arg (
					 name = "tag",
					 optional = true,
					type = IType.STRING, 
					doc = @doc ("tag of the geometries in Unity")),
			@arg (
					 name = "is_3D",
					 optional = true,
					type = IType.BOOL, 
					doc = @doc ("tag of the rgdfdhffhtftfjfjt in Unity")),
			 @arg (
				name = "height",
				type = IType.FLOAT, 
				doc = @doc ("height of the geometries in Unity")),
			 @arg (
				name = "collider",
				type = IType.BOOL, 
				doc = @doc ("Add a collider to the geometries in Unity?")) },
			doc = { @doc (
					value = "Add background geometries from a list of geometries,a optional list of name (one per geometry), their heights, their collider usage, and an optional tag")})
	public void primAddBackgroundData(final IScope scope) throws GamaRuntimeException {
		final IList geoms = (IList) scope.getArg("geoms", IType.LIST);
		final Double height = (Double) scope.getFloatArg("height");
		final Boolean collider = (Boolean) scope.getBoolArg("collider");
		final Boolean is3D = (Boolean) scope.getBoolArg("is_3D");
		final String tag = scope.hasArg("tag") ? (String) scope.getStringArg("tag") : null;
		final IList names = scope.hasArg("names") ? (IList) scope.getArg("names", IType.LIST) : null;
		
		
		addBackgroundGeometries(geoms, names, height, collider, tag,  is3D);
	}

	
	
	@action (
			name = LOC_TO_SEND,
			doc = @doc (
					returns = "the location to send to Unity"))
	public GamaPoint primLocToSend(final IScope scope) throws GamaRuntimeException {
		return scope.getAgent().getLocation();

	}
	
	@action (
			name = ADD_TO_MAP,
					args = { @arg (
							name = "map",
							type = IType.MAP,
							doc = @doc ("map of data to send to Unity")),
							@arg (
									name = "ag",
									type = IType.AGENT,
									doc = @doc ("Agent to send to Unity"))},
			doc = @doc (
					returns = "other elements than the location to add to the data sent to Unity"))
	public void primAddToMap(final IScope scope) throws GamaRuntimeException {
		
	}
	
	@action (
			name = TO_MAP,
					args = { @arg (
							name = "precision",
							type = IType.INT,
							doc = @doc ("precision of the data to send (number of decimals)")),
							@arg (
									name = "ag",
									type = IType.AGENT,
									doc = @doc ("Agent to send to Unity"))
						
			},
				
			doc = @doc (
					returns = "a map containing all the information to sent to unity concerning an agent"))
	public IMap<String, IList<Integer>> primToMap(final IScope scope) throws GamaRuntimeException {
		Integer precision = scope.getIntArg("precision");
		IAgent ag = (IAgent) scope.getArg("ag");
		IMap<String, IList<Integer>> map = GamaMapFactory.create();
		
		IList<Integer> vals = GamaListFactory.create();
		vals.add(getIndexSpecies(ag));
		vals.add(ag.getIndex());
		vals.add((int)(ag.getLocation().x * precision));
		vals.add((int)(ag.getLocation().y * precision));
		vals.add((int)(getHeading(ag) * precision));
		map.put("v", vals);
		
		Arguments args = new Arguments();
		args.put("map", ConstantExpressionDescription.create(map));
		args.put("ag", ConstantExpressionDescription.create(ag));
		
		WithArgs actATM = getAgent().getSpecies().getAction(ADD_TO_MAP);
		
		actATM.setRuntimeArgs(scope, args);
		actATM.executeOn(scope);
		
		return map;

	}
	
	public Integer getIndexSpecies(IAgent agent) {
		return (Integer) agent.getAttribute(SPECIES_INDEX);
	}
	
	public Double getHeading(IAgent agent) {
		if (agent.hasAttribute(HEADING))
			return (Double) agent.getAttribute(HEADING);
		return 0.0;
	}
	

}
