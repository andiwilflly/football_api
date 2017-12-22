(ns football-api.handler
  (:require
            [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [ajax.core :as ajax]
            [hiccup.page :refer [include-js include-css html5]]
            [football_api.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]

            [taoensso.timbre :as timbre :refer [info]]
            [cheshire.core :as json]
  ))


(def mount-target
  [:div#app
      [:h3 "ClojureScript has not been compiled!"]
      [:p "please run "
       [:b "lein figwheel"]
       " in order to start the compiler"]])


(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])


(def artists(atom 6))

(defn loading-page []
  (html5
    (head)
    [:body {:class "body-container"}
     mount-target
     (include-js "/js/app.js")]))


(defn callback [resp]
	(reset! artists  resp))

(ajax/GET "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=fb9d42de15720bcb20e6ed6fc5016a4c&artist=Cher&album=Believe&format=json"
               {:handler callback})


(defn artists-top [request]
	(response @artists))


(defn album_data [artist album]
	(info ">>>>>>>>>>> <<<<<<<<<<<<<<WE HERE!!!>>>>>>>>>>>>>>")
	(response {:artist artist :album album}))

(defn get_album [artist album]
	(ajax/GET "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=fb9d42de15720bcb20e6ed6fc5016a4c&artist=Cher&album=Believe&format=json"
	          {:handler album_data}))


(defroutes routes
    (GET "/" [] (loading-page))
    (GET "/about" [] (loading-page))
	(GET "/user/:userId" [userId] (loading-page))
    (GET "/artists/top" [] (response@artists))
    (GET "/album/:artist/:album" [artist album] (get_album artist album))

    (resources "/")
    (not-found "Not Found"))

(def app (-> routes (wrap-json-body)
             (wrap-json-response)
             (wrap-defaults api-defaults)))
