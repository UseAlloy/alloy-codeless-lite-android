import alloy from '@alloyidentity/web-sdk';
import './App.css';

function App() {
    window.StartAlloy = async function(settings) {
        try {
            await alloy.init({
                ...settings,
                isAndroidDevice: true,
            });
            let alloyCallback = function(data) {
                let json = JSON.stringify(data);
                console.log(json);
                window.CallbackObject.startAlloy(json);
            }
            await alloy.open(alloyCallback, document.getElementById('root'));
        } catch (e) {
            console.log(JSON.stringify(e));
            window.CallbackObject.gotError(JSON.stringify(e));
        }
    }

    window.CreateJourneyApplication = async function(settings, journeyData) {
        alloy.init(settings);
        try {
            const result = await alloy.createJourneyApplication(journeyData);
            console.log(result);
            window.CallbackObject.journeyApplicationTokenCreated(result);
        } catch (e) {
            console.log(JSON.stringify(e));
            window.CallbackObject.gotError(JSON.stringify(e));
        }
    }
}

export default App;
