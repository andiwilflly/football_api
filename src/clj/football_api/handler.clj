(ns football-api.handler
  (:require
            [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [ajax.core :as ajax]
            [hiccup.page :refer [include-js include-css html5]]
            [football_api.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            [taoensso.timbre :as timbre :refer [log]]
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


(defn loading-page []
  (html5
    (head)
    [:body {:class "body-container"}
     mount-target
     (include-js "/js/app.js")]))


(def a(atom 6))
(defn callback [resp]
	(log " ===== ======== =====" (str resp))
	(reset! a 6789))

(ajax/GET "http://ws.audioscrobbler.com/2.0/?method=album.search&album=believe&api_key=fb9d42de15720bcb20e6ed6fc5016a4c&format=json"
               {:handler callback})


(defn handler [resp] (response {
	:status 200
    :content-type "application/json; charset=UTF-8"
	:artists "??" }))

(defn artists-top [request]
	(response {:artists @a }))


(defroutes routes
    (GET "/" [] (loading-page))
    (GET "/about" [] (loading-page))
	(GET "/user/:userId" [userId] (loading-page))
    (GET "/artists/top" [] (wrap-json-response artists-top))

    (resources "/")
    (not-found "Not Found"))

(def app (wrap-middleware #'routes))
