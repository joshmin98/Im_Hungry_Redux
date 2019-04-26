import * as React from 'react';
import { useState, useEffect } from 'react';
import { withRouter } from 'react-router-dom';
import {
  Card,
  CardContent,
  Button,
  Theme,
  createStyles,
  withStyles,
} from '@material-ui/core';
import Header from './sub-components/Header';
import * as axios from 'axios';

// const hostname = 'http://localhost:8338/list?listName=';

const styles = (theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
    },
    drawerHeader: {
      display: 'flex',
      alignItems: 'center',
      padding: '0 8px',
      ...theme.mixins.toolbar,
      justifyContent: 'flex-start',
    },
    main: {
      marginTop: 10,
    },
    card: {
      marginBottom: 10,
    },
    container: {
      marginLeft: '5%',
      marginRight: '5%',
    },
  });

const ListManagementPage: React.FC<any> = props => {
  const [list, setList]: any = useState([]);
  const { classes } = props;

  useEffect(() => {
    axios.default
      .get('http://localhost:8338/list', {
        withCredentials: true,
        params: {
          listName: 'Favorites',
        },
      })
      .then(resp => {
        console.log('LIST', resp.data);
        setList(resp.data);
      });
  }, []);

  const reorder = (list: any, startIdx: number, endIdx: number) => {
    if (endIdx === -1) {
      return;
    } else if (endIdx === list.length) {
      return;
    }

    const result = Array.from(list);
    const [removed] = result.splice(startIdx, 1);
    result.splice(endIdx, 0, removed);
    console.log(startIdx, endIdx);
    axios.default
      .get(
        `http://localhost:8338/reorder?listName=Favorites&oldPosition=${startIdx}&newPosition=${endIdx}`,
      )
      .then(resp => {
        console.log(resp);
        setList(result);
      });
  };

  return (
    <div className={classes.root}>
      <Header />
      <div className={classes.container}>
        <h1>{props.location.pathname.slice(7)}</h1>
        {list.map((item: any, idx: number) => (
          <Card className={classes.card} key={`item-${item.id}`}>
            <CardContent>
              {item.type === 'restaurant' ? (
                <div>
                  <h2>{item.name}</h2>
                  <p>
                    <span>
                      <b>Estimated Driving Time:</b>
                    </span>
                  </p>
                  <p>
                    <span>
                      <b>Address:</b>
                    </span>
                  </p>
                  <p>
                    <span>
                      <b>Phone:</b>
                    </span>
                  </p>
                </div>
              ) : (
                  <div>
                    <h2>{item.title}</h2>
                    <p>
                      <span>
                        <b>Cook Time:</b> {item.cookingMinutes}
                      </span>
                    </p>
                    <p>
                      <span>
                        <b>Prep Time:</b> {item.preparationMinutes}
                      </span>
                    </p>
                    <p>
                      <span>
                        <b>Price Per Serving:</b> ${item.pricePerServing / 100}
                      </span>
                    </p>
                  </div>
                )}
            </CardContent>
            <Button
              id={`item-${item.id}-up`}
              onClick={() => reorder(list, idx, idx - 1)}
            >
              Up
            </Button>
            <Button
              id={`item-${item.id}-down`}
              onClick={() => reorder(list, idx, idx + 1)}
            >
              Down
            </Button>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default withStyles(styles)(withRouter(ListManagementPage));
