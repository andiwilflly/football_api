(ns example.handler
    "Asynchronous compojure-api application."
    (:require [compojure.api.sweet :refer :all]
              [ring.util.http-response :refer :all]
              [manifold.deferred :as d]
              [clojure.core.async :as async]
              compojure.api.async

              [ajax.core :as ajax]
              ))


(def app
    (api
     {:swagger
      {:ui "/"
       :spec "/swagger.json"
       :data {:info {:title "Simple"
                     :description "Compojure Api example"}
              :tags [{:name "api", :description "some apis"}]}}}

     (context "/api" []
              :tags ["api"]

              (GET "/divide" []
                   :return {:result Float}
                   :query-params [x :- Long, y :- Long]
                   :summary "divide two numbers together"
                   (let [chan (async/chan)]
                       (future
                           (async/go
                            (async/<! (async/timeout 2500))
                            (async/<! (ajax/GET "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=fb9d42de15720bcb20e6ed6fc5016a4c&artist=Cher&album=Believe&format=json"))

                            (try
                                (async/>! chan (ok {:result (float (/ x y))}))
                                (catch Throwable e
                                    (async/>! chan e))
                                (finally
                                    (async/close! chan)))
                            ))

                       chan)))

     (context "/resource" []
              (resource
               {:responses {200 {:schema {:total Long}}}
                :handler (fn [_ respond _]
                             (future
                                 (respond (ok {:total 42})))
                             nil)}))))
