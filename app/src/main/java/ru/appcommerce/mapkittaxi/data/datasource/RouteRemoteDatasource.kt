package ru.appcommerce.mapkittaxi.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouterType
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.runtime.Error
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.appcommerce.mapkittaxi.data.model.RouteResult
import kotlin.coroutines.resume

class RouteRemoteDatasource {

    private val _route: MutableLiveData<RouteResult> = MutableLiveData()
    val route: LiveData<RouteResult> get() = _route

    private val drivingRoute = DirectionsFactory.getInstance().createDrivingRouter(DrivingRouterType.COMBINED)

    private suspend fun getMyLocation() = suspendCancellableCoroutine {
        MapKitFactory.getInstance().createLocationManager().requestSingleUpdate(object :
            LocationListener {
            override fun onLocationUpdated(location: Location) {
                it.resume(RequestPoint(location.position, RequestPointType.WAYPOINT, null, null))
            }
            override fun onLocationStatusUpdated(p0: LocationStatus) = Unit
        })
    }

    suspend fun requestRoutes(toPoint: Point) {
        val waypoints = listOf(RequestPoint(toPoint, RequestPointType.WAYPOINT, null, null), getMyLocation())
        drivingRoute.requestRoutes(
            waypoints,
            DrivingOptions().apply { routesCount = 1 },
            VehicleOptions(),
            object : DrivingSession.DrivingRouteListener {
                override fun onDrivingRoutes(route: MutableList<DrivingRoute>) {
                    val routePosition = route.map { it.geometry }
                    _route.postValue(RouteResult.Success(routePosition))
                }
                override fun onDrivingRoutesError(error: Error) {
                    //TODO: сделать обработку ошибок
                    _route.postValue(RouteResult.Error("Невозможно построить маршрут"))
                }
            }
        )
    }

}